package GameObject;

import java.util.EnumSet;
import java.util.Set;


public enum Messages{
	
	VALID_PLACEMENT{
		public String toString() {
			return "\n Placed. \r\n";
		}
	},
	VALID{
	},
	INVALID_PLACEMENT{
		public String toString() {
			return "\n There is an error with your ship placements. Please try again \r\n";
		}
	},
    HIT_SINK{
        public String toString(){
            return "\nShip was hit! You sunk a ship! \r\n";
        }
    },
    HIT{
        public String toString(){
            return "\nShip was hit! No ship was sunk... \r\n";
        }
    },
    MISS{
        public String toString(){
            return "\nMissed! \r\n";
        }
    },
    DIRECTION{
        public String toString(){
            return "\n Error: Invalid direction... \r\n";
        }
    },
    OPTION{
        public String toString(){
            return "\n Error: Invalid option... \r\n";
        }
    },
    REPEAT_MISS{
        public String toString(){
            return "\n Error: Repeated Miss... Please try again. \r\n";
        }
    },
    REPEAT_HIT{
        public String toString(){
            return "\n Error: Repeated Hit... Please try again. \r\n";
        }
    },
    NUMBER{
        public String toString(){
            return "\n Error: Invalid number... Please try again. \r\n";
        }
    },  
    SHIP_IN_WAY{
        public String toString(){
            return "\n Error: Ship in way... Please try again. \r\n";
        }
    }, 
    OUT_OF_BOUNDS{
        public String toString(){
            return "\n Error: Out of bounds... Please try again. \r\n";
        }
    },
    PLAYER{
        public String toString(){
            return "\n Error: Player not found... \r\n";
        }
    },
    ENTRY{
        public String toString(){
            return "\n Error: Invalid entry... \r\n";
        }
    },
    ROCK{
        public String toString(){
            return "\n Error: Rock in way... Please try again. \r\n";
        }
    }

}

//public EnumSet<Messages> VALID = EnumSet.of(Messages.HIT, Messages.MISS);


    