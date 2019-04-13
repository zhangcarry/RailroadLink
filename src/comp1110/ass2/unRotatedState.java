package comp1110.ass2;

public class unRotatedState {
        private String US;
        public String unRotated(String a) {
            switch (a.substring(0,2)){
                case "S0":
                    US = "HHRH";
                    break;
                case "S1":
                    US = "HRRR";
                    break;
                case "S2":
                    US = "HHHH";
                    break;
                case "S3":
                    US = "RRRR";
                    break;
                case "S4":
                    US = "HRRH";
                    break;
                case "S5":
                    US = "HRHR";
                    break;
                case "A0":
                    US = "R00R";
                    break;
                case "A1":
                    US = "R0R0";
                    break;
                case "A2":
                    US = "RRR0";
                    break;
                case "A3":
                    US = "HHH0";
                    break;
                case "A4":
                    US = "H0H0";
                    break;
                case "A5":
                    US = "H00H";
                    break;
                case "B0":
                    US = "H0R0";
                    break;
                case "B1":
                    US = "HR00";
                    break;
                case "B2":
                    US = "HRHR";
                    break;
            }
            return US;
        }
    }

