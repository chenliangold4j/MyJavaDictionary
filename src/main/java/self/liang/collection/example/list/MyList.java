package self.liang.collection.example.list;

import java.lang.reflect.Array;
import java.util.Spliterator;
import java.util.function.Consumer;

public class MyList implements Spliterator<Integer> {

    /**
     *如果剩余元素存在，则对其执行给定的操作，返回true;否则返回false。
     * 如果这个Spliterator是有序的，那么将按相遇顺序对下一个元素执行操作
     * 。该操作引发的异常将传递给调用者。
     */
    @Override
    public boolean tryAdvance(Consumer<? super Integer> action) {
        return false;
    }

    @Override
    public Spliterator<Integer> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return 0;
    }

    @Override
    public int characteristics() {
        return 0;
    }
    
    int  i ;
    
    MyList(int i){
       this.i = i;
    }
    
    public int getI()
   {
      return i;
   }

   public void setI(int i)
   {
      this.i = i;
   }

   
   public static void main(String[] args) {
        Object[] test = new MyList[3];
        test[0] = new MyList(1);
        test[1] = new MyList(2);
        test[2] = new MyList(3);
      for(MyList myList:copyOf(test,2,MyList[].class)){
           myList.setI(5);
           System.out.println(myList);
      }
      
      for(Object myList:test){
         System.out.println(myList);
     }
    }

    public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {

        System.out.println(newType.getComponentType());

        @SuppressWarnings("unchecked")
        T[] copy = ((Object)newType == (Object)Object[].class)
                ? (T[]) new Object[newLength]
                : (T[]) Array.newInstance(newType.getComponentType(), newLength);
        System.arraycopy(original, 0, copy, 0,
                Math.min(original.length, newLength));
        return copy;
    }

   @Override
   public String toString()
   {
      return "MyList [i=" + i + "]";
   }

    
    
}
