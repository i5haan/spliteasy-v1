package model;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class MapUserLogin {
	
	static Map<Integer,String> usermap=new HashMap<Integer,String>();
	
	Random rand= new Random();
	public void putMapData(String id)
	{
		usermap.put(rand.nextInt(1000), id);
		System.out.println(usermap);
		
	}
	public boolean checkMapData(String id)
    {
          return usermap.containsValue(id);      
    }
    
    public boolean removeMapData(String id)
    {
          int k = 0;
          for(int key : usermap.keySet()){
            if(usermap.get(key).equals(id)){
                k = key;
            }
        }
          if(k != 0)
          {
                 usermap.remove(k);
                 return true;
          }
          else
                 return false;
    }
    
    public void showMapData()
    {
          System.out.println(usermap);
    }


}
