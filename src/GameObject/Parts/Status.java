package GameObject;

enum Status{ 
	MISS{
        public String toString(){
            return "~";
        }
    }, 
    HIT{
        public String toString(){
            return "X";
        }
    }, 
    BLANK{
        public String toString(){
            return "O";
        }
    }
};