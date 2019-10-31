package self.liang.xml.example;

import java.util.Map;

public class XMLCommonBean
{

   private String root;
   
   private String main;
   
   private Map<String, Object> dataMap;

   public String getRoot()
   {
      return root;
   }

   public void setRoot(String root)
   {
      this.root = root;
   }

   public String getMain()
   {
      return main;
   }

   public void setMain(String main)
   {
      this.main = main;
   }

   public Map<String, Object> getDataMap()
   {
      return dataMap;
   }

   public void setDataMap(Map<String, Object> dataMap)
   {
      this.dataMap = dataMap;
   }
   
   
}
