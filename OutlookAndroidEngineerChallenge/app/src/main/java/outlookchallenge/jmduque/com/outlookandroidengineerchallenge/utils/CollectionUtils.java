package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils;

import android.support.annotation.Nullable;

import java.util.Collection;

/**
 * Created by Jose on 7/28/2016.
 * This class defines different helper methods commonly required for managing collections
 * with proper null checking to avoid exceptions caused by null pointers.
 */
public class CollectionUtils {

    /**
     * @return size of the collection, if collection equals null returns 0
     */
    public static int size(
            @Nullable Collection<?> collection
    ) {
        if (collection == null) {
            return 0;
        }

        return collection.size();
    }

    /**
     * @return True if collection is empty or null
     */
    public static boolean isEmpty(
            @Nullable Collection<?> collection
    ) {
        if (collection == null) {
            return true;
        }

        return collection.isEmpty();
    }

    /**
     * @return true if the position is within [0, collection.size)
     * false if collection null or outside margin
     */
    public static boolean isValidPosition(
            @Nullable Collection<?> collection,
            int position
    ) {
        if (position < 0) {
            return false;
        }

        return position < size(collection);
    }

}
