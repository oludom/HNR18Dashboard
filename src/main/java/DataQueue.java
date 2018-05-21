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

  public synchronized int getElement(int i){
    return data[i];
  }

  public synchronized int[] getUntilFF(){

    // data array has to be long enough
    if(data.length<2) return new int[0];

    // consume until first FF
    while (data[0] != 255 || data[1] != 255){
      pop(1);
      if(data.length<2) return new int[0];
    }

    int fIndex = 0;

    // get index of next FF
    for(int i = 2; i<data.length-1; i++){
      if(data[i] == 255 && data[i+1] == 255){
        fIndex = i;
        break;
      }
    }

    if(fIndex > 2){
      return pop(fIndex);
    }else return new int[0];

  }


}
