package org.approvaltests;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.JunitReporter;
import org.approvaltests.reporters.intellij.IntelliJReporter;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.support.ModifierSupport;
import org.lambda.functions.Functions;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

public class CheckedExceptionsTest
{
  @Test
  void testTheVerifyApi()
  {
    // Get all of the classes for all the packages
    Queryable<Class<?>> classes = getAllClasses();
    // Filter for methods in the classes that have a checked exception
    Queryable<Method> methods = classes.selectMany(s -> getMethodsWithCheckedExceptions(s))
        .orderBy(m -> m.toString());
    // Verify the methods
    GenericDiffReporter reporter = new GenericDiffReporter("C:\\Program Files\\JetBrains\\IntelliJ IDEA 2021.2.1\\bin\\idea64.exe", "diff %s %s", "");

    Options options = new Options();
//    options = options.withReporter(reporter);
    Approvals.verifyAll("Methods with checked exceptions", methods, c -> c.toString(), options);
  }
  private List<Method> getMethodsWithCheckedExceptions(Class<?> aClass)
  {
    Method[] declaredMethods = aClass.getDeclaredMethods();
    return Query.where(declaredMethods,
        m -> m.getExceptionTypes().length != 0 && Modifier.isPublic(m.getModifiers()));
  }
  private Queryable<Class<?>> getAllClasses()
  {
    File[] files = FileUtils.getRecursiveFileList(new File(".."), f -> f.getName().endsWith(".java"));
    Queryable<String> paths = Query.select(files,
        Functions.unchecked(f -> f.getCanonicalPath().replace(File.separatorChar, '.')));
    paths = paths.where(p -> p.contains("main"))
        .where(p -> p.contains(".approvaltests.src.") || p.contains(".approvaltests-util."));
    return paths.select(f -> getJavaClass(f));
  }
  private Class<?> getJavaClass(String path)
  {
    String className = path.substring(0, path.length() - ".java".length());
    if (className.contains(".com."))
    {
      className = className.substring(className.indexOf(".com.") + 1);
    }
    if (className.contains(".org."))
    {
      className = className.substring(className.indexOf(".org.") + 1);
    }
    try
    {
      Class<?> aClass = Class.forName(className);
      return aClass;
    }
    catch (Throwable e)
    {
      SimpleLogger.variable("Path", path);
      throw ObjectUtils.throwAsError(e);
    }
  }
}
