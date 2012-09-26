function FuzzyComparator(keywords) {

    var adaptForComparison = function (str) {
        return str.replace(/[\s+\.]]/, ' ').toLowerCase();
    }

    var bigramize = function (keyword) {
        var iter = 0;
        var bigrams = new Array();
        for (iter = 0; iter < keyword.length; iter = iter + 2) {
            if (iter + 1 < keyword.length) {
                bigrams.push((keyword.charCodeAt(iter) << 8) | keyword.charCodeAt(iter + 1));
            }
//            else {
//                bigrams.push(keyword.charCodeAt(iter) << 8);
//            }
        }
        return bigrams;
    };

    var bigramizeCandidate = function (candidate) {
        var tokens = candidate.split(' ');
        var tokensAndBigramsForCandidate = [];

        for (tokenIndex in tokens) {
            if (!tokensAndBigramsForCandidate[tokens[tokenIndex]]) {
                tokensAndBigramsForCandidate[tokens[tokenIndex]] = new Array();
            }
            try {
                tokensAndBigramsForCandidate[tokens[tokenIndex]].push(bigramize(tokens[tokenIndex]));
            } catch (e) {
                console.log(e)
                console.log(tokens[tokenIndex])
                console.log(tokensAndBigramsForCandidate[tokens[tokenIndex]])
            }

        }
        ;
        return tokensAndBigramsForCandidate;
    };


    var compareInternal = function (candidateBigrams, compareToBigrams) {
        var score = 0.0;
        var matches = 0;

        for (iter in candidateBigrams) {
            //console.log(candidateBigrams[iter] + ":" + compareToBigrams[iter]);
            if (candidateBigrams[iter] == compareToBigrams[iter]) {
                matches++;
            }
        }

        return (2 * matches) / (candidateBigrams.length + compareToBigrams.length);

    };


    var calcBigramsetSize = function (bigramSet) {
        var retSize = 0;
        for (bigramIndex in bigramSet) {
            retSize = retSize + bigramSet[bigramIndex].length;
        }
        ;

        return retSize;
    };

    this.fuzzyCompare = function (userInput, candidateInput) {
        var inputBigramsAndTokenSet = bigramizeCandidate(adaptForComparison(userInput));
        var candidateBigramsAndTokenSet = bigramizeCandidate(adaptForComparison(candidateInput));

        //var inputTokenSize = Object.keys(inputBigramsAndTokenSet).length;
        //var candidateTokenSize = Object.keys(candidateBigramsAndTokenSet).length;

        var inputTokenSize = calcBigramsetSize(inputBigramsAndTokenSet);
        var candidateTokenSize = calcBigramsetSize(candidateBigramsAndTokenSet);


        var bestPossibleScore = (inputTokenSize > candidateTokenSize) ? inputTokenSize : candidateTokenSize;
        var totalScore = 0.0;

        var bestCandidateIndex = null;
        var bestInputIndex = null;

        do {

            var bestScore = 0.0;
            var bestCandidateIndex = null;
            var bestInputIndex = null;

            for (inputIndex in inputBigramsAndTokenSet) {
                if (inputBigramsAndTokenSet[inputIndex].length == 0) continue;
                var bigramsForInputToken = inputBigramsAndTokenSet[inputIndex][0];
                var score = 0.0;
                for (candidateIndex in candidateBigramsAndTokenSet) {
                    if (candidateBigramsAndTokenSet[candidateIndex].length == 0) continue;

                    var candidateTokenBigrams = candidateBigramsAndTokenSet[candidateIndex][0];

                    score = compareInternal(candidateTokenBigrams, bigramsForInputToken);

                    if (score >= bestScore) {
                        bestScore = score;
                        bestCandidateIndex = candidateIndex;
                        bestInputIndex = inputIndex;
                    }
                }

            }
            if (bestScore != 0) {
                inputBigramsAndTokenSet[bestInputIndex].pop();
                candidateBigramsAndTokenSet[bestCandidateIndex].pop();
            }

            totalScore = totalScore + bestScore;
        } while (bestScore != 0);


        return (totalScore / bestPossibleScore) * 100;

    };


    //Optimized Levenstein distance algorithm by akidee  - https://gist.github.com/527658

    var levenshtein = function () {

        var min = Math.min;
        try {
            split = !('0')[0];
        } catch (i) {
            split = true;
        }

        return function (a, b) {

            if (a == b) return 0;
            if (!a.length || !b.length) return b.length || a.length;


            if (split) {

                a = a.split('');
                b = b.split('');
            }
            ;
            var l_a = a.length + 1,
                l_b = b.length + 1,
                I = 0,
                i = 0,
                d = [0],
                c, j = 0, J,
                ld;
            while (++j < l_b) d[j] = j;
            i = 0;
            while (++i < l_a) {
                J = j = 0;
                c = a[I];

                ld = d.slice(0);
                d = [i];

                while (++j < l_b) {

                    d[j] = min(
                        ld[j] + 1,
                        d[J] + 1,
                        ld[J] + (c != b[J])
                    );
                    ++J;
                }
                ++I;
            }

            return d[l_b - 1];
        }
    }();

    //End of levenstein implementation


    this.fuzzyCompareAndRank = function (userInput) {
        var results = new Array();
        var score = 0.0;
        for (keywordIndex in keywords) {
            score = this.fuzzyCompare(keywords[keywordIndex], userInput);
            if (score > 0) {
                var result = new Object();
                result.text = keywords[keywordIndex];
                result.weightage = score;
                results.push(result);
            }
        }

        results.sort(function sortfunc(result1, result2) {
            return result2.weightage - result1.weightage;
        });

        results.sort(function conditionalLevensteinSort(result1, result2) {
            if (result2.weightage == result1.weightage) {
                return levenshtein(result1.text, userInput) - levenshtein(result2.text, userInput);
            }
            return 0;
        });

        return results;
    };


    this.fuzzyCompareAndRankObjects = function (userInput, filterCriteria, evaluationClosure) {
        var results = new Array();
        var score = 0.0;
        for (var keywordIndex in keywords) {
            score = this.fuzzyCompare(evaluationClosure(keywords[keywordIndex]), userInput);

            if (score > filterCriteria) {
                var result = new Object();
                result.object = keywords[keywordIndex];
                result.weightage = score;
                results.push(result);
            }
        }

        results.sort(function sortfunc(result1, result2) {
            return result2.weightage - result1.weightage;
        });

        results.sort(function conditionalLevensteinSort(result1, result2) {
            if (result2.weightage == result1.weightage) {
                return levenshtein(evaluationClosure(result1.object), userInput) - levenshtein(evaluationClosure(result2.object), userInput);
            }
            return 0;
        });

        return results;
    };

}