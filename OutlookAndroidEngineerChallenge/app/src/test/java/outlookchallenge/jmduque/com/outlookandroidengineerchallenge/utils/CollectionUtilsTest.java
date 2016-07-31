package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jose on 7/31/2016.
 */
public class CollectionUtilsTest {

    @Test
    public void testSize() throws Exception {
        assertEquals(
                "Null array returns error",
                0,
                CollectionUtils.size(null)
        );

        List<String> array = new ArrayList<>();
        assertEquals(
                "Non-null array empty case error",
                0,
                CollectionUtils.size(array)
        );

        array.add("1");
        array.add("2");
        array.add("3");
        assertEquals(
                "Non-empty array empty case error",
                3,
                CollectionUtils.size(array)
        );
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertEquals(
                "Null array returns error",
                true,
                CollectionUtils.isEmpty(null)
        );

        List<String> array = new ArrayList<>();
        assertEquals(
                "Non-null array empty case error",
                true,
                CollectionUtils.isEmpty(array)
        );

        array.add("1");
        array.add("2");
        array.add("3");
        assertEquals(
                "Non-empty array empty case error",
                false,
                CollectionUtils.isEmpty(array)
        );
    }

    @Test
    public void testIsValidPosition() throws Exception {

        assertEquals(
                "Null array returns error",
                false,
                CollectionUtils.isValidPosition(
                        null,
                        0
                )
        );

        List<String> array = new ArrayList<>();
        assertEquals(
                "Non-null array empty case error",
                false,
                CollectionUtils.isValidPosition(
                        array,
                        0
                )
        );

        array.add("1");
        array.add("2");
        array.add("3");
        assertEquals(
                "Non-empty array empty valid position case error",
                true,
                CollectionUtils.isValidPosition(
                        array,
                        0
                )
        );

        assertEquals(
                "Non-empty array empty invalid position case error",
                false,
                CollectionUtils.isValidPosition(
                        array,
                        5
                )
        );

        assertEquals(
                "Non-empty array empty invalid position case error (below zero)",
                false,
                CollectionUtils.isValidPosition(
                        array,
                        -1
                )
        );
    }
}