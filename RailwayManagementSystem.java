import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

class Train {
    private String trainName;
    private int trainNumber;
    private int platformNumber;

    public Train(String name, int number, int platform) {
        trainName = name;
        trainNumber = number;
        platformNumber = platform;
    }

    public String getTrainName() {
        return trainName;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public int getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(int platform) {
        platformNumber = platform;
    }

    public String toString() {
        return trainName + " (Train No. " + trainNumber + ") at Platform No. " + platformNumber;
    }
}

public class RailwayManagementSystem {
    private static Vector<Train> trains = new Vector<>();
    private static Vector<Integer> platforms = new Vector<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            System.out.println("Welcome to Railway Management System");
            System.out.println("1. Add Train");
            System.out.println("2. Allocate Platform");
            System.out.println("3. Deallocate Platform");
            System.out.println("4. View All Trains");
			System.out.println("5. Write Data To File");
            System.out.println("0. Exit");
            System.out.print("Enter your option: ");
            option = input.nextInt();

            switch (option) {
                case 1:
                    addTrain();
                    break;
                case 2:
                    allocatePlatform();
                    break;
                case 3:
                    deallocatePlatform();
                    break;
                case 4:
                    viewTrains();
                    break;
				case 5:
					writeTrains();
					break;
                case 0:
                    System.out.println("Thank you for using Railway Management System!");
                    writeTrains();
                    break;
                default:
                    System.out.println("Invalid option!");
            }

            System.out.println();
        }
    }

    private static void addTrain() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter train name: ");
        String name = input.nextLine();

        System.out.print("Enter train number: ");
        int number = input.nextInt();

        trains.add(new Train(name, number, -1));

        System.out.println("Train added successfully!");
    }

    private static void allocatePlatform() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter train number: ");
        int number = input.nextInt();

        System.out.print("Enter platform number: ");
        int platform = input.nextInt();

        Train train = findTrain(number);
        if (train != null) {
            if (!platforms.contains(platform)) {
                platforms.add(platform);
                train.setPlatformNumber(platform);
                System.out.println("Train " + train.getTrainName() + " allocated to platform " + platform);
            } else {
                System.out.println("Platform " + platform + " is already occupied!");
            }
        } else {
            System.out.println("Train not found!");
        }
    }

    private static void deallocatePlatform() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter train number: ");
        int number = input.nextInt();

        Train train = findTrain(number);
        if (train != null) {
            int platform = train.getPlatformNumber();
            if (platform != -1) {
                platforms.remove(new Integer(platform));
                train.setPlatformNumber(-1);
                System.out.println("Train " + train.getTrainName() + " deallocated from platform " + platform);
            } else {
                System.out.println("Train " + train.getTrainName() + " is not allocated to any platform!");
            }
        } else {
            System.out.println("Train not found!");
        }
    }

    private static void viewTrains() {
        System.out.println("List of all trains:");
        for (Train train : trains) {
            System.out.println(train);
        }
    }

    private static Train findTrain(int number) {
        for (Train train : trains) {
            if (train.getTrainNumber() == number) {
                return train;
            }
        }
        return null;
    }

    private static void writeTrains() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("trains.txt")));

            for (Train train : trains) {
                writer.write(train.getTrainName() + "," + train.getTrainNumber() + "," + train.getPlatformNumber());
                writer.newLine();
            }

            writer.close();
            System.out.println("Train information written to file trains.txt");
        } catch (IOException e) {
            System.out.println("Failed to write train information to file: " + e.getMessage());
        }
    }
}

