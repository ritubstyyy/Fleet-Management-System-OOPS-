import exceptions.InvalidOperationException;
import exceptions.OverloadException;
import fleet.FleetManager;
import interfaces.FuelConsumable;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import vehicles.Airplane;
import vehicles.Bus;
import vehicles.Car;
import vehicles.CargoShip;
import vehicles.Truck;
import vehicles.Vehicle;

public class Main {
   private static final FleetManager fm = new FleetManager();
   private static final Scanner sc;

   public Main() {
   }

   public static void main(String[] var0) {
      runCLI();
   }

   private static void demoSetup() {
      try {
         Car var0 = new Car("C001", "Toyota", 120.0, 4);
         var0.refuel(40.0);
         var0.boardPassengers(2);
         Truck var1 = new Truck("T001", "Volvo", 90.0, 6);
         var1.refuel(200.0);
         var1.loadCargo(2000.0);
         Bus var2 = new Bus("B001", "MercedesBus", 80.0, 6);
         var2.refuel(300.0);
         var2.boardPassengers(30);
         var2.loadCargo(100.0);
         Airplane var3 = new Airplane("A001", "Boeing", 900.0, 10000.0);
         var3.refuel(5000.0);
         var3.boardPassengers(100);
         var3.loadCargo(2000.0);
         CargoShip var4 = new CargoShip("S001", "Maersk", 40.0, false);
         var4.refuel(10000.0);
         var4.loadCargo(20000.0);
         fm.addVehicle(var0);
         fm.addVehicle(var1);
         fm.addVehicle(var2);
         fm.addVehicle(var3);
         fm.addVehicle(var4);
         System.out.println("Simulating a 100 km journey for demo vehicles...");
         fm.startAllJourneys(100.0);
         System.out.println(fm.generateReport());
      } catch (OverloadException | InvalidOperationException var5) {
         System.out.println("Demo setup error: " + var5.getMessage());
      }

   }

   private static void runCLI() {
      while(true) {
         showMenu();
         int var0 = readInt();

         try {
            switch (var0) {
               case 1 -> addVehicleCLI();
               case 2 -> removeVehicleCLI();
               case 3 -> startJourneyCLI();
               case 4 -> refuelAllCLI();
               case 5 -> performMaintenanceCLI();
               case 6 -> System.out.println(fm.generateReport());
               case 7 -> saveFleetCLI();
               case 8 -> loadFleetCLI();
               case 9 -> searchByTypeCLI();
               case 10 -> searchByIdCLI();
               case 11 -> listMaintenanceNeedsCLI();
               case 12 -> {
                   System.out.println("Exiting...");
                   return;
                 }
               default -> System.out.println("Invalid option.");
            }
         } catch (Exception var2) {
            System.out.println("Operation error: " + var2.getMessage());
         }
      }
   }

   private static void showMenu() {
      System.out.println("\n--- Fleet Manager Menu ---");
      System.out.println("1. Add Vehicle");
      System.out.println("2. Remove Vehicle");
      System.out.println("3. Start Journey");
      System.out.println("4. Refuel All");
      System.out.println("5. Perform Maintenance");
      System.out.println("6. Generate Report");
      System.out.println("7. Save Fleet");
      System.out.println("8. Load Fleet");
      System.out.println("9. Search by Type");
      System.out.println("10. Search by ID");
      System.out.println("11. List Vehicles Needing Maintenance");
      System.out.println("12. Exit");
      System.out.print("Choose an option: ");
   }

   private static void addVehicleCLI() {
      System.out.println("Enter vehicle type (Car/Truck/Bus/Airplane/CargoShip): ");
      String var0 = sc.next();

      try {
         System.out.print("Enter ID: ");
         String var1 = sc.next();
         System.out.print("Enter model: ");
         String var2 = sc.next();
         System.out.print("Enter max speed (km/h): ");
         double var3 = sc.nextDouble();
         int var12;
         switch (var0) {
            case "Car" -> {
                System.out.print("Enter numWheels: ");
                var12 = sc.nextInt();
                Car var15 = new Car(var1, var2, var3, var12);
                fm.addVehicle(var15);
                System.out.println("Car added.");
              }
            case "Truck" -> {
                System.out.print("Enter numWheels: ");
                var12 = sc.nextInt();
                Truck var14 = new Truck(var1, var2, var3, var12);
                fm.addVehicle(var14);
                System.out.println("Truck added.");
              }
            case "Bus" -> {
                System.out.print("Enter numWheels: ");
                var12 = sc.nextInt();
                Bus var13 = new Bus(var1, var2, var3, var12);
                fm.addVehicle(var13);
                System.out.println("Bus added.");
              }
            case "Airplane" -> {
                System.out.print("Enter maxAltitude: ");
                double var11 = sc.nextDouble();
                Airplane var9 = new Airplane(var1, var2, var3, var11);
                fm.addVehicle(var9);
                System.out.println("Airplane added.");
              }
            case "CargoShip" -> {
                System.out.print("Has sail (true/false): ");
                boolean var7 = sc.nextBoolean();
                CargoShip var8 = new CargoShip(var1, var2, var3, var7);
                fm.addVehicle(var8);
                System.out.println("CargoShip added.");
              }
            default -> System.out.println("Unknown type.");
         }
      } catch (InvalidOperationException var10) {
         System.out.println("Error adding vehicle: " + var10.getMessage());
      }

   }

   private static void removeVehicleCLI() {
      System.out.print("Enter vehicle ID to remove: ");
      String var0 = sc.next();

      try {
         fm.removeVehicle(var0);
         System.out.println("Removed " + var0);
      } catch (InvalidOperationException var2) {
         System.out.println(var2.getMessage());
      }

   }

   private static void startJourneyCLI() {
      System.out.print("Enter distance (km) for the journey: ");
      double var0 = sc.nextDouble();
      fm.startAllJourneys(var0);
   }

   private static void refuelAllCLI() {
      System.out.print("Enter fuel amount to add to each FuelConsumable vehicle: ");
      double var0 = sc.nextDouble();
       for (Vehicle var3 : fm.getFleet()) {
           Objects.requireNonNull(var3);
          switch (var3) {
              case FuelConsumable fuelVehicle -> {
                  try {
                      fuelVehicle.refuel(var0);
                  } catch (InvalidOperationException ex) {
                      System.out.println("Refuel error for " + var3.getId() + ": " + ex.getMessage());
                  }
              }
              case CargoShip ship -> {
                  if (!ship.hasSail()) {
                      try {
                          ship.refuel(var0);
                      } catch (InvalidOperationException ex) {
                          System.out.println("Refuel error for " + ship.getId() + ": " + ex.getMessage());
                      }
                  }
              }
              default -> {
              }
          }
       }

      System.out.println("Refuel attempt completed.");
   }

   private static void performMaintenanceCLI() {
      fm.maintainAll();
      System.out.println("Maintenance performed where needed.");
   }

   private static void saveFleetCLI() {
      System.out.print("Enter filename to save fleet: ");
      String var0 = sc.next();
      fm.saveToFile(var0);
   }

   private static void loadFleetCLI() {
      System.out.print("Enter filename to load fleet: ");
      String var0 = sc.next();
      fm.loadFromFile(var0);
   }

   private static void searchByTypeCLI() {
      System.out.print("Enter type to search (Car/Truck/Bus/Airplane/CargoShip): ");
      List var10000;
      var10000 = switch (sc.next()) {
           case "Car" -> fm.searchByType(Car.class);
           case "Truck" -> fm.searchByType(Truck.class);
           case "Bus" -> fm.searchByType(Bus.class);
           case "Airplane" -> fm.searchByType(Airplane.class);
           case "CargoShip" -> fm.searchByType(CargoShip.class);
           default -> List.of();
       };

      List var1 = var10000;
      System.out.println("Found: " + var1.size());
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         Vehicle var4 = (Vehicle)var2.next();
         var4.displayInfo();
      }

   }

   private static void searchByIdCLI() {
      System.out.print("Enter vehicle ID to search: ");
      String var0 = sc.next();
      Vehicle var1 = null;
      Iterator var2 = fm.getFleet().iterator();

      while(var2.hasNext()) {
         Vehicle var3 = (Vehicle)var2.next();
         if (var3.getId().equalsIgnoreCase(var0)) {
            var1 = var3;
            break;
         }
      }

      if (var1 != null) {
         System.out.println("Vehicle found:");
         var1.displayInfo();
      } else {
         System.out.println("No vehicle found with ID: " + var0);
      }

   }

   private static void listMaintenanceNeedsCLI() {
      List var0 = fm.getVehiclesNeedingMaintenance();
      if (var0.isEmpty()) {
         System.out.println("No vehicles need maintenance.");
      } else {
         System.out.println("Vehicles needing maintenance:");
         Iterator var1 = var0.iterator();

         while(var1.hasNext()) {
            Vehicle var2 = (Vehicle)var1.next();
            PrintStream var10000 = System.out;
            String var10001 = var2.getId();
            var10000.println(" - " + var10001 + " (" + var2.getClass().getSimpleName() + ")");
         }
      }

   }

   private static int readInt() {
      try {
         return sc.nextInt();
      } catch (Exception var1) {
         sc.nextLine();
         return -1;
      }
   }

   static {
      sc = new Scanner(System.in);
   }
}
