package glavnipaket;



import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class NRNFunctionalWithStreams implements NajveciNizInterfejs {

    @Override
    public ArrayList<Integer> dajNajveciRastuciNiz(int[] niz) {
        ArrayList<ArrayList<Integer>> allGrowingLists = findAllGrowingLists(niz);
        return findTheLongestList(allGrowingLists);
    }


    public static ArrayList<ArrayList<Integer>> findAllGrowingLists(int[] array){
        ArrayList<ArrayList<Integer>> growingLists = new ArrayList<>();

 /*       for(int i=0; i<array.length; i++){
            growingLists = newListsAfterParsingTheNextNumber(growingLists, array[i]);
        } */
        growingLists = parseNewElement(array, 0, growingLists);

        return growingLists;
    }

    public static ArrayList<ArrayList<Integer>> newListsAfterParsingTheNextNumber(ArrayList<ArrayList<Integer>> growingLists, final int NEXT_NUMBER){

        final ArrayList<ArrayList<Integer>> listsThatMapToThemselves = growingLists.stream()
                .filter(aGrowingList -> getLastElement(aGrowingList) >= NEXT_NUMBER)
                .collect(Collectors.toCollection(ArrayList::new));

        final ArrayList<ArrayList<Integer>> newLists = growingLists.stream()
                .map(aGrowingList -> createNewList(aGrowingList, NEXT_NUMBER))
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));

        growingLists = copyList(listsThatMapToThemselves, listsThatMapToThemselves.size());
        growingLists.addAll(newLists);

        growingLists.add(createList(NEXT_NUMBER));
        return growingLists;
    }

    public static ArrayList<ArrayList<Integer>> parseNewElement(int[] array,
                                                                int currentIndex,
                                                                ArrayList<ArrayList<Integer>> growingLists){
        if(currentIndex >= array.length) return growingLists;

        growingLists = newListsAfterParsingTheNextNumber(growingLists, array[currentIndex]);

        final int NEXT_INDEX = currentIndex + 1;
        if(array.length > NEXT_INDEX){
            growingLists = parseNewElement(array, NEXT_INDEX, growingLists);
        }

        return growingLists;
    }


    /* Vraca null ako nijedan element liste nije manji od drugog ulaznog parametra.
     * U suprotnom, trazi se poslednji clan liste koji je manji od drugog ulaznog parametra (nazovimo ga ,,clan X").
     *  Deo liste do clana X se kopira (zakljucno sa clanom X), te se kopiranoj listi dodaje drugi ulazni parametar.
     *  Metoda vraca takvu listu. */
    public static ArrayList<Integer> createNewList(ArrayList<Integer> oldList, int newElement){
        final int LAST_INDEX = oldList.size() -1;

        for(int i=LAST_INDEX; i>=0; i--){
            if(oldList.get(i) < newElement){
                final int NUMBER_OF_ELEMENTS_TO_COPY = i+1;
                ArrayList<Integer> newList = copyList(oldList, NUMBER_OF_ELEMENTS_TO_COPY);
                newList.add(newElement);
                return newList;
            }
        }
        return null;
    }

    public static <T> ArrayList<T> copyList(ArrayList<T> list, int numberOfElementsToCopy){
        if(list == null) return null;
        numberOfElementsToCopy = Math.min(numberOfElementsToCopy, list.size());

        ArrayList<T> newList = new ArrayList<>();
        for(int i=0; i<numberOfElementsToCopy; i++){
            newList.add(list.get(i));
        }

        return newList;
    }

    public static ArrayList<Integer> createList(int broj){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(broj);
        return list;
    }


    public static <T> ArrayList<T> findTheLongestList(ArrayList<ArrayList<T>> lists){
        if(lists == null) return null;

        ArrayList<T> longestList = new ArrayList<>();
        for(ArrayList<T> list : lists){
            if(list.size() > longestList.size()){
                longestList = list;
            }
        }

        return longestList;
    }

    public static int getLastElement(ArrayList<Integer> list){
        return list.get(list.size() - 1);
    }
}
