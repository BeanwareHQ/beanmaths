FUNCTION SortPlayerData(Data)
    # Data: List of Players.
    # Player: Structure with a name field and a score field. Somewhat like a class or a C style struct.

    FOR i <- 1 TO Data.length DO
        # Last index for the sublist currently to be sorted.
        SubloopTarget <- Data.length - i
        FOR j <- 1 to SubloopTarget DO
            # If the previous player's score is less than the current player's score,
            # swap.
            IF Data[j-1].score > Data[j].score THEN
                Temp <- Data[j]
                Data[j] <- Data[j-1]
                Data[j-1] <- Data[j]
            ENDIF
        NEXT j
    NEXT i

    # now the list has been sorted.
    #
    # Since the entire player structure was shuffled, when
    # one loops through the list, they can just access the
    # name field to get the corresponding player name.
ENDFUNCTION