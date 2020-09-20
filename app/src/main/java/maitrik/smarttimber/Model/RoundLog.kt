package maitrik.smarttimber.Model

class RoundLog {
    var rlid:Int=0
    var rllenght:Double=0.0
    var rlperimeter:Double=0.0
    var rlqty:Int=0
    var rlcft:Double=0.0
    var rlcmt:Double=0.0



    constructor()
    constructor(rllenght: Double, rlperimeter: Double, rlqty: Int, rlcft: Double, rlcmt: Double) {
        this.rllenght = rllenght
        this.rlperimeter = rlperimeter
        this.rlqty = rlqty
        this.rlcft = rlcft
        this.rlcmt = rlcmt
    }

}