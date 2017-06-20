package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling;

public class Controlling {
    Move NextMove;

    public Move next() {
        if(NextMove == null || NextMove instanceof Turn){
            NextMove = new Forward(500 /*TEMP*/);
            return NextMove;
        }
        else if(NextMove instanceof Forward){



            return NextMove;
        }
        else{
            return null;
        }
    }
}
