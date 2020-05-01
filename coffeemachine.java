
package machine;

import java.util.Arrays;
import java.util.Scanner;
import java.util.SortedMap;

public class CoffeeMachine {

    public static void main(String[] args) {
        Machine machine = new Machine();
        boolean isWorking = true;

        do {
            isWorking = machine.checkForAction(machine.askForInput());
        } while (isWorking);
    }
}
class Machine {
    static Scanner scanner = new Scanner(System.in);
    int[] ingredients = {400, 540, 120, 9, 550}; //starting values

    enum MachineState {
        CHOOSE, BUYING, FILLING;
    }

    MachineState machineState = MachineState.CHOOSE;

    //print ingredient available
    private void currentStatus(int[] ingredients) {
        System.out.println("The coffee machine has: ");
        System.out.printf("%d of water\n", ingredients[0]);
        System.out.printf("%d of milk\n", ingredients[1]);
        System.out.printf("%d of coffee beans\n", ingredients[2]);
        System.out.printf("%d of disposable cups\n", ingredients[3]);
        System.out.printf("%d of money\n", ingredients[4]);
    }

    public String askForInput() {
        if (machineState == MachineState.CHOOSE) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
        } 
        return scanner.nextLine();
    }
    // asks for action
    public boolean checkForAction(String actionTake) {
        boolean isWorking = true;
        switch (machineState) {
            case CHOOSE:
            switch (actionTake) {
                case "buy":
                    machineState = MachineState.BUYING;
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappucino, back - to main menu:");
                    checkForAction(askForInput());
                    break;
                case "fill":
                    machineState = MachineState.FILLING;
                    checkForAction("");
                    break;
                case "take":
                    this.ingredients[4] = takingMoney(this.ingredients[4]);
                    break;
                case "remaining":
                    currentStatus(this.ingredients);
                    break;
                case "exit":
                    isWorking = false;
                    break;
            }
                break;
            case BUYING:
                this.ingredients = makingCoffee(actionTake, this.ingredients);
                machineState = MachineState.CHOOSE;
                break;
            case FILLING:
                this.ingredients = fillingMachine(this.ingredients);
                machineState = MachineState.CHOOSE;
                break;
        }
        return isWorking;
    }


    //checks for supplies
    private boolean isEnough(int[] ingredients, String typeOfCoffee) {
        boolean canMakeCoffee = true;
        switch (typeOfCoffee) {
            case "1": //espresso
                if (ingredients[0] >= 250 && ingredients[2] >= 16 && ingredients[3] >= 1) {
                    System.out.println("I have enough resources, making you a coffee!");
                } else if (ingredients[0] < 250) {
                    System.out.println("Sorry, not enough water!");
                    canMakeCoffee = false;
                } else if (ingredients[2] < 16) {
                    System.out.println("Sorry, not enough coffee beans!");
                    canMakeCoffee = false;
                } else if (ingredients[3] == 0) {
                    System.out.println("Sorry, not enough disposable cups");
                    canMakeCoffee = false;
                }
                break;
            case "2": //latte
                if (ingredients[0] >= 350 && ingredients[1] >= 75 && ingredients[2] >= 20 && ingredients[3] >= 1) {
                    System.out.println("I have enough resources, making you a coffee!");
                } else if (ingredients[0] < 350) {
                    System.out.println("Sorry, not enough water!");
                    canMakeCoffee = false;
                } else if (ingredients[1] < 75) {
                    System.out.println("Sorry, not enough milk!");
                    canMakeCoffee = false;
                } else if (ingredients[2] < 20) {
                    System.out.println("Sorry, not enough coffee beans!");
                    canMakeCoffee = false;
                } else if (ingredients[3] == 0) {
                    System.out.println("Sorry, not enough disposable cups");
                    canMakeCoffee = false;
                }
                break;
            case "3": //cappuccino
                if (ingredients[0] >= 200 && ingredients[1] >= 100 && ingredients[2] >= 12 && ingredients[3] >= 1) {
                    System.out.println("I have enough resources, making you a coffee!");
                } else if (ingredients[0] < 200) {
                    System.out.println("Sorry, not enough water!");
                    canMakeCoffee = false;
                } else if (ingredients[1] < 100) {
                    System.out.println("Sorry, not enough milk!");
                    canMakeCoffee = false;
                } else if (ingredients[2] < 12) {
                    System.out.println("Sorry, not enough coffee beans!");
                    canMakeCoffee = false;
                } else if (ingredients[3] == 0) {
                    System.out.println("Sorry, not enough disposable cups");
                    canMakeCoffee = false;
                }
                break;
        }
        return canMakeCoffee;
    }

    //making coffee & updating available supplies
    private int[] makingCoffee(String answer, int[] ingredients) {
        boolean canMakeCoffee;
        switch (answer) {
            case "1": //espresso
                canMakeCoffee = isEnough(ingredients, "1");
                if (canMakeCoffee) {
                    ingredients[0] -= 250;
                    ingredients[2] -= 16;
                    ingredients[3] -= 1;
                    ingredients[4] += 4;
                }
                break;
            case "2": //latte
                canMakeCoffee = isEnough(ingredients, "2");
                if (canMakeCoffee) {
                    ingredients[0] -= 350;
                    ingredients[1] -= 75;
                    ingredients[2] -= 20;
                    ingredients[3] -= 1;
                    ingredients[4] += 7;
                }
                break;
            case "3": //cappuccino
                canMakeCoffee = isEnough(ingredients, "3");
                if (canMakeCoffee) {
                    ingredients[0] -= 200;
                    ingredients[1] -= 100;
                    ingredients[2] -= 12;
                    ingredients[3] -= 1;
                    ingredients[4] += 6;
                }
                break;
            case "back":
                break;
        }
        return ingredients;
    }

    //filling machine with amounts of choice
    private int[] fillingMachine(int[] ingredients) {
        System.out.println("Write how many ml of water do you want to add:");
        ingredients[0] += Integer.parseInt(askForInput());
        System.out.println("Write how many ml of milk do you want to add:");
        ingredients[1] += Integer.parseInt(askForInput());
        System.out.println("Write how many grams of coffee beans do you want to add:");
        ingredients[2] += Integer.parseInt(askForInput());
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        ingredients[3] += Integer.parseInt(askForInput());
        return ingredients;
    }

    //withdrawing ALL the money
    private int takingMoney(int money) {
        System.out.printf("I gave you $%d\n", money);
        return 0;
    }
}
