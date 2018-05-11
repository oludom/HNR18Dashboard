public class DataQueue {


  private int[] data;

  public DataQueue(){
    data = new int[0];
  }

  public synchronized void push(int b){
    int[] tmp = new int[data.length+1];
    for(int i = 0; i<data.length;i++){
      tmp[i] = data[i];
    }
    tmp[data.length] = b;
    data = tmp;
  }

  public synchronized int[] pop(int i){
    int[] out = new int[i];
    for(int j = 0; j<i; j++){
      out[j] = data[j];
    }
    int[] tmp = new int[data.length-i];
    for(int j = 0; j<data.length-i; j++){
      tmp[j] = data[j+i];
    }
    data = tmp;
    return out;
  }

  public synchronized String toString(){
    String tmp = "";
    for(int d : data){
      tmp += d;
    }
    return tmp;
  }

  public synchronized int getLength(){
    return data.length;
  }

  public synchronized int getFirst(){
    return data[0];
  }
  public synchronized int getSecond(){
    return data[1];
  }
  public synchronized int getThird(){
    return data[2];
  }

}
