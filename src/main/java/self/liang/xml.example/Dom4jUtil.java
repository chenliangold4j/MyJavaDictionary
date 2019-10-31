package self.liang.xml.example;


import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class Dom4jUtil
{

   /**
    * 通过xml获取document
    *
    * @param xml
    * @return
    * @throws DocumentException
    * @throws UnsupportedEncodingException
    */
   public static Document parse(String xml)
   {
      SAXReader reader = new SAXReader();
      StringReader stringReader = new StringReader(xml);
      Document document = null;
      try
      {
         document = reader.read(stringReader);
      }
      catch (DocumentException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return document;
   }

   // ---------------------------------------------------------------------------

   public static String getRootString( Document document){
      Element element =  document.getRootElement();
      String value = element.getText();
      return  value;
   }


   public static Element getElementFromBean(String elementName, Object obj)
   {
      Element element = DocumentHelper.createElement(elementName);
      Field[] fields = obj.getClass().getDeclaredFields();
      for (Field field : fields)
      {
         try
         {
            field.setAccessible(true);
            if (Date.class.isAssignableFrom(field.getType()) && field.get(obj) != null)
            {
               Date date = (Date) field.get(obj);
               SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               String valString = formatter.format(date);
               String name = field.getName();
               if (name != null && valString != null)
                  element.addElement(name).setText(valString);
            }
            else
            {
               String name = field.getName();
               String valString = field.get(obj) != null ? field.get(obj).toString() : null;
               if (name != null && valString != null)
                  element.addElement(name).setText(valString);
            }
         }
         catch (IllegalArgumentException e)
         {
            e.printStackTrace();
         }
         catch (IllegalAccessException e)
         {
            e.printStackTrace();
         }
      }
      return element;
   }
   // ---------------------------------------------------------------------------

   public static Element getElementFromBean(String root,String elementName, Object obj)
   {
      Element result = DocumentHelper.createElement(elementName);
      Element element = DocumentHelper.createElement(elementName);
      Field[] fields = obj.getClass().getDeclaredFields();
      for (Field field : fields)
      {
         try
         {
            field.setAccessible(true);
            if (Date.class.isAssignableFrom(field.getType()) && field.get(obj) != null)
            {
               Date date = (Date) field.get(obj);
               SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               String valString = formatter.format(date);
               String name = field.getName();
               if (name != null && valString != null)
                  element.addElement(name).setText(valString);
            }
            else
            {
               String name = field.getName();
               String valString = field.get(obj) != null ? field.get(obj).toString() : null;
               if (name != null && valString != null)
                  element.addElement(name).setText(valString);
            }
         }
         catch (IllegalArgumentException e)
         {
            e.printStackTrace();
         }
         catch (IllegalAccessException e)
         {
            e.printStackTrace();
         }
      }
      result.add(element);
      return result;
   }
   // ---------------------------------------------------------------------------


   @SuppressWarnings("unchecked")
   public static <T> T ElementConvertToBean(Element main, Class<T> clazz)
   {
      T result = null;
      try
      {
         result = clazz.newInstance();
      }
      catch (Exception e)
      {
         return result;
      }
      Field[] fields = clazz.getDeclaredFields();
      Element son = main;
      for (Iterator<Element> it = son.elementIterator(); it.hasNext(); )
      {
         Element element = it.next();

         if (element.hasMixedContent())
         {
            String name = element.getName();
            for (Field field : fields)
            {
               String fiedlName = AnnotationUtil.getXmlAnnotationValue(field, XmlName.class);
               if (fiedlName == null)
               {
                  fiedlName = field.getName();
               }
               if (fiedlName.equals(name))
               {
                  Object bean = ElementConvertToBean(element, field.getType());
                  if (bean != null)
                  {
                     field.setAccessible(true);
                     try
                     {
                        field.set(result, bean);
                     }
                     catch (IllegalAccessException e)
                     {
                        e.printStackTrace();
                     }
                  }
               }
            }
         }
         else
         {
            String name = element.getName();
            for (Field field : fields)
            {
               String fiedlName = AnnotationUtil.getXmlAnnotationValue(field, XmlName.class);
               if (fiedlName == null)
               {
                  fiedlName = field.getName();
               }
               if (fiedlName.equals(name))
               {
                  String value = element.getText();
                  if (isNotBlank(value))
                  {
                     Object obj = ConverterUtil.converterToBaseType(value, field.getType());
                     if (obj != null)
                     {
                        field.setAccessible(true);
                        try
                        {
                           field.set(result, obj);
                        }
                        catch (IllegalArgumentException e)
                        {
                           e.printStackTrace();
                        }
                        catch (IllegalAccessException e)
                        {
                           e.printStackTrace();
                        }
                     }
                  }
               }
            }
         }
      }
      return result;
   }
   // ---------------------------------------------------------------------------

   public static  <T> T getBeanFromElement(Element root, Class<T> clazz){
      Iterator<Element> eleIterator = root.elementIterator();
      Element son = eleIterator.next();
      T result = ElementConvertToBean(son,clazz);
      return  result;
   }

   /**
    * 从xml中获取公共bean 返回的格式详见bean
    *
    * @param xml
    * @return
    * @throws UnsupportedEncodingException
    * @throws DocumentException
    */
   public static XMLCommonBean getXmlBeanFromXml(String xml) throws DocumentException
   {
      XMLCommonBean xmlCommonBean = new XMLCommonBean();
      Map<String, Object> map = null;
      Document document = parse(xml);
      Element root = document.getRootElement();
      if (root != null)
      {
         xmlCommonBean.setRoot(root.getName());// 设置root节点
         @SuppressWarnings("unchecked")
         Iterator<Element> eleIterator = root.elementIterator();
         Element son = eleIterator.next();
         if (son != null)
         {
            xmlCommonBean.setMain(son.getName());// 设置main节点
            map = getMapFromELement(son);
            xmlCommonBean.setDataMap(map);// 设置datamap数据
         } // --------End If--------
      } // --------End If--------
      return xmlCommonBean;
   }
   // ---------------------------------------------------------------------------

   public static XMLCommonBean getXmlBeanFromXml(Element element)
   {
      XMLCommonBean xmlCommonBean = new XMLCommonBean();
      Map<String, Object> map = null;
      Element root = element;
      if (root != null)
      {
         xmlCommonBean.setRoot(root.getName());// 设置root节点
         @SuppressWarnings("unchecked")
         Iterator<Element> eleIterator = root.elementIterator();
         Element son = eleIterator.next();
         if (son != null)
         {
            xmlCommonBean.setMain(son.getName());// 设置main节点
            map = getMapFromELement(son);
            xmlCommonBean.setDataMap(map);// 设置datamap数据
         } // --------End If--------
      } // --------End If--------
      return xmlCommonBean;
   }
   // ---------------------------------------------------------------------------


   private static boolean isNotBlank(String value)
   {
      if (value == null) return false;
      value = value.trim();
      if (value.length() > 0) return true;
      else
         return false;
   }
   // ---------------------------------------------------------------------------

   /**
    * 当有多个子节点时，，返回子节点列表
    **/
   public static List<Map<String, Object>> getListBeanFromElement(Element root)
   {
      List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
      if (root != null)
      {
         @SuppressWarnings("unchecked")
         Iterator<Element> eleIterator = root.elementIterator();
         while (eleIterator.hasNext())
         {
            Element son = eleIterator.next();
            if (son != null)
            {
               Map<String, Object> map = getMapFromELement(son);
               result.add(map);
            } // --------End If--------
         }
      } // --------End If--------
      return result;
   }
   // ---------------------------------------------------------------------------

   /**
    * 当有多个子节点时，，返回子节点列表
    **/
   public static<T> List<T> getListBeanFromElement(Element root,Class<T> clazz)
   {
      List<T> result = new ArrayList<T>();
      if (root != null)
      {
         @SuppressWarnings("unchecked")
         Iterator<Element> eleIterator = root.elementIterator();
         while (eleIterator.hasNext())
         {
            Element son = eleIterator.next();
            if (son != null)
            {
               T bean = ElementConvertToBean(son,clazz);
               result.add(bean);
            } // --------End If--------
         }
      } // --------End If--------
      return result;
   }
   // ---------------------------------------------------------------------------

   public static Map<String, List<String>> getMapListFromElement(Element root)
   {
      Map<String, List<String>> result = new HashMap<String, List<String>>();
      List<Map<String, Object>> list = getListBeanFromElement(root);
      for (Map<String, Object> map : list)
      {
         for (String key : map.keySet())
         {
            if (result.containsKey(key) == false)
            {
               result.put(key, new ArrayList<String>());
               result.get(key).add(map.get(key).toString());
            }
            else
            {
               result.get(key).add(map.get(key).toString());
            }
         }
      }
      return result;
   }
   // ---------------------------------------------------------------------------


   /**
    * 获取自节点的datamap
    *
    * @param xml
    * @return
    * @throws UnsupportedEncodingException
    * @throws DocumentException
    */
   public static Map<String, Object> getDataMapFromXml(String xml) throws UnsupportedEncodingException, DocumentException
   {
      Map<String, Object> result = null;
      Document document = parse(xml);
      Element root = document.getRootElement();
      if (root != null)
      {
         @SuppressWarnings("unchecked")
         Iterator<Element> eleIterator = root.elementIterator();
         Element son = eleIterator.next();
         if (son != null)
         {
            result = getMapFromELement(son);
         } // --------End If--------
      } // --------End If--------
      return result;
   }
   // ---------------------------------------------------------------------------


   public static Element getElementFromMap(String root,String main,Map<String,Object> map)
   {
      Element result = DocumentHelper.createElement(root);
      result.add(mapConvertToElement(main,map));
      return result;
   }
   // ---------------------------------------------------------------------------

   public static Element mapConvertToElement(String elementName,Map<String,Object> map) {
      Element result = DocumentHelper.createElement(elementName);
      for(String key:map.keySet()){
         if(map.get(key) instanceof  Map){
            result.add(mapConvertToElement(key,(Map<String,Object>)map.get(key)));
         }else {
            result.addElement(key).setText(map.get(key).toString());
         }
      }
      return result;
   }


   /**
    * 从节点中获取 递归获取map 当有子节点时 value为map 否则为String
    *
    * @param elements
    * @return
    */
   @SuppressWarnings("unchecked")
   private static Map<String, Object> getMapFromELement(Element elements)
   {
      Map<String, Object> map = new HashMap<String, Object>();
      for (Iterator<Element> it = elements.elementIterator(); it.hasNext(); )
      {
         Element element = it.next();
         if (element.hasMixedContent())
         {
            String key = element.getName();
            Map<String, Object> sonMap = getMapFromELement(element);
            map.put(key, sonMap);
         }
         else
         {
            String key = element.getName();
            String value = element.getText();
            map.put(key, value);
         } // --------End If--------
      } // --------End For--------
      return map;
   }
   // ---------------------------------------------------------------------------

   /**
    * 创建只有一个子节点的Element
    *
    * @param root
    * @param main
    * @param key
    * @param value
    * @return
    */
   public static Element createResponseXmlSingleElement(String root, String main, String key, String value)
   {
      Element rootEle = DocumentHelper.createElement(root);
      Element mainEle = rootEle.addElement(main);
      mainEle.addElement(key).setText(value);
      return rootEle;
   }
   // ---------------------------------------------------------------------------

   public static Element createResponseXmlSingleElement(String root, String main, String key, int i)
   {
      return createResponseXmlSingleElement(root, main, key, String.valueOf(i));
   }

   // ---------------------------------------------------------------------------
   public static Element createResponseXmlSingleElement(String root, String main, String key, Long i)
   {
      return createResponseXmlSingleElement(root, main, key, String.valueOf(i));
   }
   // ---------------------------------------------------------------------------

   /**
    * 主节点下只有单层的xml
    *
    * @param root
    * @param main
    * @param data
    * @return
    */
   public static Element createResponseXmlMonolayer(String root, String main, Map<String, String> data)
   {
      Element rootEle = DocumentHelper.createElement(root);
      Element mainEle = rootEle.addElement(main);
      for (String key : data.keySet())
      {
         mainEle.addElement(key).setText(data.get(key));
      }
      return rootEle;
   }
   // ---------------------------------------------------------------------------

   /**
    * 新增一个子节点
    */
   public static Element addSonElement(Element rootELe, String key, String value)
   {
      @SuppressWarnings("unchecked")
      Iterator<Element> eleIterator = rootELe.elementIterator();
      Element son = eleIterator.next();
      son.addElement(key).setText(value);
      return rootELe;
   }
   // ---------------------------------------------------------------------------

   /*查找孙子节点*/

   public static String findGrandsonELement(Element root,String grandsonName){
      String result = null;
      Iterator<Element> eleIterator = root.elementIterator();
      Element son = eleIterator.next();
      for(Iterator<Element> iterator = son.elementIterator();iterator.hasNext();){
         Element element = iterator.next();
         if(element.getName().equals(grandsonName)){
            result = element.getText();
         }
      }
      return result;
   }

   /**
    * 把document转xml
    *
    * @param document
    * @return
    */
   public static String asXML(org.dom4j.Node document)
   {
      if (document == null)
         return null;

      OutputFormat format = new OutputFormat();
      format.setEncoding("UTF-8");
      try
      {
         StringWriter out = new StringWriter();
         StandaloneWriter writer = new StandaloneWriter(out, format);
         if (document instanceof Document == false)
            writer.startDocument();
         writer.write(document);
         if (document instanceof Document == false)
            writer.endDocument();
         writer.flush();
         return out.toString();
      }
      catch (Exception e)
      {
         throw new RuntimeException("IOException while generating textual " + "representation: " + e.getMessage());
      } // --------End Try--------
   }
   // ---------------------------------------------------------------------------
   
   public static void main(String[] args)
   {
      String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\r\n" + 
               "<DeviceManager>  \r\n" + 
               "<Add>\r\n" + 
               "<login_id>1070000035917</login_id>     \r\n" + 
               "<name>test</name>    \r\n" + 
               "<remark>详细信息</remark>   \r\n" + 
               "<deviceNumber>XVJ5-MUBK-UHS5-Y</deviceNumber>    \r\n" + 
               "<device_type_id>3</device_type_id>    \r\n" + 
               "<address >2222</address>      \r\n" + 
               "<ip>192.168.0.54</ip>    \r\n" + 
               "<port>7777</port>    \r\n" + 
               "<status>1</status>    \r\n" + 
               "<modify_time>2018-11-30 11:16:29</modify_time>    \r\n" + 
               "<valid_type>4</valid_type> \r\n" + 
               "</Add>\r\n" + 
               "</DeviceManager>";
      Document document = parse(xml);
   }

}

// -------------------------------------end class-----------------------------
// 构造头的writer
class StandaloneWriter extends XMLWriter
{

   public StandaloneWriter(StringWriter out, OutputFormat format)
   {
      super(out, format);
   }
   // ---------------------------------------------------------------------------

   protected void writeDeclaration() throws IOException
   {
      OutputFormat format = getOutputFormat();
      String encoding = format.getEncoding();
      if (!format.isSuppressDeclaration())
      {
         writer.write("<?xml version=\"1.0\"");
         if (encoding.equals("UTF8"))
         {
            if (!format.isOmitEncoding())
               writer.write(" encoding=\"UTF-8\"");
         }
         else
         {
            if (!format.isOmitEncoding())
               writer.write(" encoding=\"" + encoding + "\"");

         } // --------End If--------
         writer.write(" standalone=\"yes\"");
         writer.write("?>");
         if (format.isNewLineAfterDeclaration())
            println();
      } // --------End If--------
   }
}
