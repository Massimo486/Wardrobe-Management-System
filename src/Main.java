import java.io.IOException;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Wardrobe wardrobe1 = new Wardrobe();
        Wardrobe wardrobe2 = new Wardrobe();
        wardrobe1.addPieceOfClothing("Shirt", "Red", 32.5);

        /*
        wardrobe.addPieceOfClothing("Shirt", "Red", 32.5);
        wardrobe.addPieceOfClothing("Trouser", "Red", 22);
        wardrobe.addPieceOfClothing("Jacket", "Black", 32);

        wardrobe.getSetOfClothings();
        wardrobe.searchByCategorieAndColour("Shirt", "Red");


        int countRedPieces = 0;

        for(PieceOfClothing p1 : wardrobe.getSetOfClothings()){


            if (p1.getColour() == "Red"){
                countRedPieces++;
            }

        }

        System.out.println("In the Wardrobe are " +  countRedPieces + " pieces red!");*/


        System.out.println("Server is running at Port: 6789");

        DatagramSocket socket = new DatagramSocket(6789);
        byte[] buffer = new byte[1000];

        while(true){

            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            socket.receive(request);

            System.out.println(" Request: " + new String(request.getData(), 0, request.getLength()));

            String receivedString = new String(request.getData(), 0, request.getLength());
            List<String> receivedList = seperateArg(receivedString);

            if(receivedList.get(0).equals("add")){

                double castedSize = Double.parseDouble(receivedList.get(4));

                if (receivedList.get(1).equals("wardrobe1")){

                    wardrobe1.addPieceOfClothing(receivedList.get(2), receivedList.get(3), castedSize);
                }
                else if(receivedList.get(1).equals("wardrobe2")){

                    wardrobe2.addPieceOfClothing(receivedList.get(2), receivedList.get(3), castedSize);
                }

            }
            else if(receivedList.get(0).equals("show")){

                if (receivedList.get(1).equals("wardrobe1")){

                    return wardrobe1.getSetOfClothings();
                }
                else if(receivedList.get(1).equals("wardrobe2")){

                    return wardrobe2.getSetOfClothings();
                }
            }


            DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
            socket.send(reply);
        }
    }

    public static List<String> seperateArg(String msg){

        List<String> stringToList = Arrays.asList(msg.split(","));
        return stringToList;
    }
}
