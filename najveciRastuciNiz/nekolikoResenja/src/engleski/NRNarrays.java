package engleski;

import glavnipaket.NajveciNizInterfejs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NRNarrays implements NajveciNizInterfejs {

    @Override
    public ArrayList<Integer> dajNajveciRastuciNiz(int[] niz) {
        int[] longestArray = getLongestGrowingArray(niz);
        return arrayToArrayList(longestArray);
    }

    private int[] getLongestGrowingArray(int[] array){
        int[][] allGrowingArrays = getAllGrowingArrays(array);
        return getLongestArray(allGrowingArrays);
    }

    private static int[][] getAllGrowingArrays(int[] array){
        int[][] allGrowingArrays = new int[0][];

        for(int i=0; i<array.length; i++){
            allGrowingArrays = getGrowingArraysAfterParsingNextElement(allGrowingArrays, array[i]);
        }

        return allGrowingArrays;
    }

    private static int[][] getGrowingArraysAfterParsingNextElement(int[][] growingArrays, final int NEXT_ELEMENT){
        int[][] allGrowingArrays = getGrowingArraysFromParsingOldArrays(growingArrays, NEXT_ELEMENT);
        int[] arrayFromNewElement = new int[]{NEXT_ELEMENT};

        return appendAnArray(allGrowingArrays, arrayFromNewElement);
    }
    
    private static int[][] getGrowingArraysFromParsingOldArrays(int[][] growingArrays, final int NEXT_ELEMENT){
        int[][] arraysThatMapToThemselves = Arrays.stream(growingArrays).
                filter(aGrowingArray -> getLastElement(aGrowingArray) >= NEXT_ELEMENT).
                toArray(int[][]::new);

        int[][] newArrays = Arrays.stream(growingArrays).
                map(aGrowingArray -> createNewGrowingArray(aGrowingArray, NEXT_ELEMENT)).
                filter(Objects::nonNull).
                toArray(int[][]::new);

        return combine2Darrays(arraysThatMapToThemselves, newArrays);
    }

/* Imagine input being a growing array {1,3,10} and the new element being 4.
The output of this function should be {1,3,4}, because that's the longest growing array that could be created
from the input array with the number 4. If the new number is smaller than all the elements of the input array,
then this function should return null, as no new growing array can be created with the number provided.*/
    private static int[] createNewGrowingArray(final int[] growingArray, int number){
        final int LAST_INDEX = growingArray.length - 1;

        for(int i=LAST_INDEX; i>=0; i--){
            if(growingArray[i] < number){
                final int NUMBER_OF_ELEMENTS_TO_COPY = i+1;
                return copyPartOfAnArrayAndAppendANumberToIt(growingArray, NUMBER_OF_ELEMENTS_TO_COPY, number);
            }
        }

        return null;
    }

    private static int[] copyPartOfAnArrayAndAppendANumberToIt(final int[] array,
                                                final int NUMBER_OF_ELEMENTS_TO_COPY,
                                                final int NUMBER_TO_ADD){
        int[] newArray = new int[NUMBER_OF_ELEMENTS_TO_COPY + 1]; // +1 because I am adding another number.
        System.arraycopy(array, 0, newArray, 0, NUMBER_OF_ELEMENTS_TO_COPY);
        newArray[NUMBER_OF_ELEMENTS_TO_COPY] = NUMBER_TO_ADD; // the last element of new growing array

        return newArray;
    }

    private static int[][] combine2Darrays(final int[][] array1, final int[][] array2){
        int[][] arrayToReturn = new int[array1.length + array2.length][];

        System.arraycopy(array1, 0, arrayToReturn, 0, array1.length);
        System.arraycopy(array2, 0, arrayToReturn, array1.length, array2.length);
        return arrayToReturn;
    }
    
    private static int[][] appendAnArray(final int[][] arrays, final int[] arrayToAdd){
        int[][] arraysToReturn = new int[arrays.length + 1][];
        System.arraycopy(arrays, 0, arraysToReturn, 0, arrays.length);
        arraysToReturn[arrays.length] = arrayToAdd;

        return arraysToReturn;
    }

    private static int getLastElement(final int[] array){
        return array[array.length-1];
    }

    private static int[] getLongestArray(final int[][] arrays){
        int[] longestArray = arrays[0];

        for(int[] array : arrays){
            if(array.length > longestArray.length){
                longestArray = array;
            }
        }

        return longestArray;
    }

    private static ArrayList<Integer> arrayToArrayList(final int[] array){
        ArrayList<Integer> list = new ArrayList<>();
        for(int element : array){
            list.add(element);
        }
        return list;
    }
}
