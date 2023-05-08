import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
        byte[] buffer = new byte[5000];

        while(true){

            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            socket.receive(request);

            System.out.println("Request: " + new String(request.getData(), 0, request.getLength()));

            String receivedString = new String(request.getData(), 0, request.getLength());
            List<String> receivedList = seperateArg(receivedString);

            if(receivedList.get(0).equals("add")){

                double castedSize = Double.parseDouble(receivedList.get(4));

                if (receivedList.get(1).equals("wardrobe1")){

                    wardrobe1.addPieceOfClothing(receivedList.get(2), receivedList.get(3), castedSize);
                    buffer = "New piece of clothing was added!".getBytes(StandardCharsets.UTF_8);
                }
                else if(receivedList.get(1).equals("wardrobe2")){

                    wardrobe2.addPieceOfClothing(receivedList.get(2), receivedList.get(3), castedSize);
                    buffer = "New piece of clothing was added!".getBytes(StandardCharsets.UTF_8);
                }

            }
            else if(receivedList.get(0).equals("show")){

                if (receivedList.get(1).equals("wardrobe1")){

                    String rep = "";
                    for(PieceOfClothing pieceOfClothing : wardrobe1.getSetOfClothings()){

                        rep = rep + "\n" + pieceOfClothing.getCategory().toString() + "\n" + pieceOfClothing.getColour().toString() + "\n" + pieceOfClothing.getSize() + "\n";
                    }

                    buffer = rep.getBytes();

                }
                else if(receivedList.get(1).equals("wardrobe2")){

                    String rep = "";
                    for(PieceOfClothing pieceOfClothing : wardrobe2.getSetOfClothings()){

                        rep = rep + "\n" + pieceOfClothing.getCategory().toString() + "\n" + pieceOfClothing.getColour().toString() + "\n" + pieceOfClothing.getSize() + "\n";
                    }

                    buffer = rep.getBytes();
                }
            }

            DatagramPacket reply = new DatagramPacket(buffer, buffer.length, request.getAddress(), request.getPort());
            socket.send(reply);
        }
    }

    public static List<String> seperateArg(String msg){

        List<String> stringToList = Arrays.asList(msg.split(","));
        return stringToList;
    }

}
