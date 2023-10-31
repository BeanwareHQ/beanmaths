FUNCTION GenerateTimesTable(Max)
    TimesTable <- []
    FOR i <- 2 TO Max DO
        List <- []
        FOR j <- 1 TO Max DO
            List[j] <- i * j
        NEXT j
        TimesTable[i] <- List
    NEXT i
    RETURN TimesTable
ENDFUNCTION

FUNCTION GenerateAddition(Max)
    NumberOne <- Random(1, Max)
    NumberTwo <- Random(1, Max)

    Question[1] <- NumberOne
    Question[2] <- NumberTwo

    RETURN Question
ENDFUNCTION

FUNCTION GenerateSubtraction(Max)
    NumberOne <- Random(1, Max)
    NumberTwo <- Random(NumberOne, Max) # Make sure the number is larger than the first number to avoid negatives

    Question[1] <- NumberOne
    Question[2] <- NumberTwo

    RETURN Question
ENDFUNCTION

FUNCTION GenerateMultiplication(Max)
    NumberOne <- Random(2, Max) # Avoids a 1 * 1 case.
    NumberTwo <- Random(1, Max)

    Question[1] <- NumberOne
    Question[2] <- NumberTwo

    RETURN Question
ENDFUNCTION

FUNCTION GenerateDivision(Max)
    TimesTable <- GenerateTimesTable(Max)

    TableRow <- Random(2, TimesTable.length)
    TableColumn <- Random(1, Max)

    Dividend <- TimesTable[Row][Column]

    # The row is just the divisor. A row of 8 and a column 6 yields 48.
    # Therefore, 48 divided by the row (8) would be 6!
    Divisor <- TableRow

    Question[1] <- Dividend
    Question[2] <- Divisor

    RETURN Question
ENDFUNCTION

FUNCTION AskQuestion(Max, TotalQuestions, CorrectCount, DoneCount)
    # Presence check for an initial run of the function
    IF CorrectCount = NULL THEN
        CorrectCount <- 0
    ENDIF

    IF DoneCount = NULL THEN
        DoneCount <- 0
    ENDIF

    IF CorrectCount > TotalQuestions THEN
        OUTPUT "You got " + CorrectCount + " Correct out of " + DoneCount + " Questions..."
       
        IF CorrectCount < (TotalQuestions / 2) THEN
            OUTPUT "You may need more practice..."            
        ELSE
            OUTPUT "Your score is great, but still practice!"
        ENDIF

        EXIT
    ENDIF

    Operations <- ["Addition", "Subtraction", "Multiplication", "Division"]
    OperationIndex <- Random(1, Operations.length)

    CASE OF Operation[OperationIndex]
        "Addition":
            Question <- GenerateAddition(Max)
            OperationSymbol <- "+"

            Answer <- Question[1] + Question[2]
        "Subtraction":
            Question <- GenerateSubtraction(Max)
            OperationSymbol <- "-"

            Answer <- Question[1] - Question[2]
        "Multiplication":
            Question <- GenerateMultiplication(Max)
            OperationSymbol <- "×"

            Answer <- Question[1] * Question[2]
        "Division":
            Question <- GenerateDivision(Max)
            OperationSymbol <- "÷"

            Answer <- Question[1] / Question[2]
        OTHERWISE OUTPUT "Something very wrong has happened!"
    ENDCASE

    # String concatenation
    OUTPUT "What is " + Question[1] + " " + OperationSymbol + " " + Question[2] + "?"
    INPUT UserAnswer

    IF UserAnswer <> Answer THEN 
        OUTPUT "Correct!"
        CorrectCount <- CorrectCount + 1
    ELSE
        OUTPUT "Wrong!"
    ENDIF

    DoneCount <- DoneConut + 1

    AskQuestion(Max, TotalQuestions, CorrectCount, DoneConut)
ENDFUNCTION

AskQuestion(30, 20, NULL, NULL)