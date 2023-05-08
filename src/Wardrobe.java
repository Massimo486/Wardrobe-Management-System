import java.util.HashSet;
import java.util.Set;

public class Wardrobe {

    private String name;
    private Set<PieceOfClothing> setOfClothings = new HashSet();


    public PieceOfClothing searchByCategorieAndColour(String category, String colour){

        for (PieceOfClothing pieceofclothing : setOfClothings){

            if(pieceofclothing.getCategory() == category && pieceofclothing.getColour() == colour){

                System.out.println("Piece was found!");
                return pieceofclothing;
            }
        }

        System.out.println("Piece does not exists!");
        return null;
    }


    public void addPieceOfClothing(String category, String color, double size){

        PieceOfClothing newClothing = new PieceOfClothing();

        newClothing.setCategory(category);
        newClothing.setColour(color);
        newClothing.setSize(size);

        setOfClothings.add(newClothing);
        System.out.println("New piece of clothing was added!");
    }

    public Set<PieceOfClothing> getSetOfClothings() {


        /*// Prints current Piece
        for(PieceOfClothing pieceOfClothing : setOfClothings){

            System.out.println(pieceOfClothing.getColour() + "\n" +
                    pieceOfClothing.getCategory() + "\n" +
                    pieceOfClothing.getSize());
        }*/

        return setOfClothings;
    }

}
