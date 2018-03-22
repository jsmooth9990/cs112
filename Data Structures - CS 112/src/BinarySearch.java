public class BinarySearch {

    /**
     * returns the index of the targer, or -1 if not found
     */
    public static int iBinarySearch(int[]A, int target){
        int l = 0; // leftmost term
        int r = A.length - 1; // right most term
        while(l <= r){
            int m = (l + r) / 2;
            if(target == A[m]){
                return m; // target found
            }else if(target < A[m]){
                r = m -1;
            }else {
                l = m + 1;
            }
        }
        return -1; // target not found
    }

    public static int rBinarySearch(int[]A, int target, int l, int r){
        if(l>r){
            return -1; // target not found
        }

        int m = (l + r) / 2;
        if(target == A[m]){
            return m;
        }else if(target <  A[m]){
            // target must be in the left half of the array
            return rBinarySearch(A, target, l, m-1);v b
        }else {
            // target must be in the right half of the array
            return rBinarySearch(A, target, m+1, r);
        }

    }

    public static void main(String[] args){
        int[] array = {2,6,10,34, 45, 66};
        System.out.println(iBinarySearch(array, 2));
        System.out.println(iBinarySearch(array, 666));

        System.out.println(rBinarySearch(array, 2, 0, array.length-1));
        System.out.println(rBinarySearch(array, 666, 0, array.length-1));
    }

}
