package pl.edu.pw.mini.jrafalko.truck;

import pl.edu.pw.mini.jrafalko.cargo.Cargo;

import java.util.HashSet;
import java.util.Set;

public class Truck {

    private DriversCabin driversCabin = new DriversCabin();
    private LoadingBody loadingBody = new LoadingBody();
    private SpareWeel spareWeel;
    private FuelTank fuelTank;

    public class DriversCabin {

        private Driver driver = new Driver();
        private IgnitionSwitch ignitionSwitch;

        public class Driver {

            private Driver() {}

            private void drive() {

                if (ignitionSwitch == null) {
                    System.out.println("Brak stacyjki? Jak go odpalić?");
                } else if (fuelTank == null || !fuelTank.isFilled()) {
                    System.out.println("Znowu zapomniałem zatankować...");
                } else if (spareWeel == null) {
                    System.out.println("Mam nadzieję, że nie złapię gumy");
                } else {
                    System.out.println("W drogę!!!");
                }
            }

        }

        private class IgnitionSwitch {

            private boolean complicated;

            public IgnitionSwitch(boolean complicated) {
                this.complicated = complicated;
            }

            private void start() {
                if (driver != null) {
                    System.out.println("Chrrrrr....");
                } else {
                    System.out.println("Bez kierowcy nie ma jazdy...");
                }
            }

        }

    }

    public class LoadingBody {

        private Set<Cargo> paceGoods = new HashSet<>();

        private void addTowar(Cargo towar) {
            paceGoods.add(towar);
        }

        private String printPaceGoods() {
            return "Towar na pace: " + paceGoods;
        }

    }

    public class FuelTank {

        private boolean filled;

        public FuelTank() {}

        public FuelTank(boolean filled) {
            this.filled = filled;
        }

        public boolean isFilled() {
            return filled;
        }

    }

}
