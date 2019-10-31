package self.liang.xml.example;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConverterUtil
{
   @SuppressWarnings("unchecked")
   public static <T> T converterToBaseType(String value, Class<T> clazz)
   {
      Object result = null;
      if (String.class.isAssignableFrom(clazz))
      {
         result = value;
      }
      else if (Integer.class.isAssignableFrom(clazz) || int.class.isAssignableFrom(clazz))
      {
         Integer intValue = Integer.parseInt(value);
         if (int.class.isAssignableFrom(clazz))
         {
            result = intValue.intValue();
         }
         else
         {
            result = intValue;
         }
      }
      else if (Long.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz))
      {
         Long longValue = Long.parseLong(value);
         if (long.class.isAssignableFrom(clazz))
         {
            result = longValue.longValue();
         }
         else
         {
            result = longValue;
         }
      }
      else if (Boolean.class.isAssignableFrom(clazz))
      {
         Boolean booleanValue = Boolean.parseBoolean(value);
         if (boolean.class.isAssignableFrom(clazz))
         {
            result = booleanValue.booleanValue();
         }
         else
         {
            result = booleanValue;
         }
      }
      else if (Float.class.isAssignableFrom(clazz) || float.class.isAssignableFrom(clazz))
      {
         Float floatValue = Float.parseFloat(value);
         if (float.class.isAssignableFrom(clazz))
         {
            result = floatValue.floatValue();
         }
         else
         {
            result = floatValue;
         }
      }
      else if (Double.class.isAssignableFrom(clazz) || double.class.isAssignableFrom(clazz))
      {
         Double dbValue = Double.parseDouble(value);
         if (double.class.isAssignableFrom(clazz))
         {
            result = dbValue.doubleValue();
         }
         else
         {
            result = dbValue;
         }
      }

      else if (Date.class.isAssignableFrom(clazz))
      {
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         try
         {
            Date date = formatter.parse(value);
            result = date;
         }
         catch (ParseException e)
         {
            e.printStackTrace();
         }
      }
      return (T) result;
   }
}
