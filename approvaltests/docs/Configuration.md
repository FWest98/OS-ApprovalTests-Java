<a id="top"></a>

# Configuration

<!-- toc -->
## Contents

  * [What is configurable?](#what-is-configurable)
    * [Package Level Settings](#package-level-settings)
  * [Alternative Base Directory for Output Files](#alternative-base-directory-for-output-files)
  * [PackageLevelSettings](#packagelevelsettings)<!-- endToc -->

## What is configurable?
Configuration of ApprovalTests mainly occurs via @Annotations and PackageSettings. 
(the API is also extendible) 
Currently you can configure:

 * [Reporters](Reporters.md#class-and-method-level) (package/class/method)
 * FrontLoadedReporters ( package/class/method)
 * ApprovalSubdirectories (package)

### Package Level Settings
The following options will be read by ApprovalTests:
<!-- snippet: ApprovalTestPackageSettingsTest.createPackageSettingDocumentation.approved.txt -->
<a id='snippet-ApprovalTestPackageSettingsTest.createPackageSettingDocumentation.approved.txt'></a>
```txt
public class PackageSettings {

    public static EnvironmentAwareReporter FrontloadedReporter = new JunitReporter();
    public static ApprovalFailureReporter UseReporter = new ClipboardReporter();
    public static String UseApprovalSubdirectory = "approvals";
    public static String ApprovalBaseDirectory = "../resources";
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/core/ApprovalTestPackageSettingsTest.createPackageSettingDocumentation.approved.txt#L1-L7' title='Snippet source file'>snippet source</a> | <a href='#snippet-ApprovalTestPackageSettingsTest.createPackageSettingDocumentation.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

The approved & received files will now be created in the subdirectory `/approvals/` for any test in the same package as this file, or any test in any subpackage under this.  

## Alternative Base Directory for Output Files  

Approved and received files can be stored in a different branch of the code base (for example, under the `/resources/` folder).

To do so, write a class as follows:    

<!-- snippet: package_settings_approval_base_directory -->
<a id='snippet-package_settings_approval_base_directory'></a>
```java
public class PackageSettings
{
  public static String ApprovalBaseDirectory = "../resources";
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/packagesettings/basedirectory/PackageSettings.java#L3-L8' title='Snippet source file'>snippet source</a> | <a href='#snippet-package_settings_approval_base_directory' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

The approved and received files will now be created in the directory `/source/test/resources/` for any test in the same package as this file, or any test in any under this.  

## PackageLevelSettings  

Package Level Settings allows for programmatic setting of configuration at the package level. It follows the principle of least surprise.   

Your Package Leveling configuration must be in class called PackageSettings. The fields can be private, public and or static. They will be picked up regardless. All methods will be ignored.

For example if you had a class:

<!-- snippet: /approvaltests-tests/src/test/java/org/packagesettings/PackageSettings.java -->
<a id='snippet-/approvaltests-tests/src/test/java/org/packagesettings/PackageSettings.java'></a>
```java
package org.packagesettings;

public class PackageSettings
{
  public String        name     = "Llewellyn";
  private int          rating   = 10;
  public static String lastName = "Falco";
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/packagesettings/PackageSettings.java#L1-L8' title='Snippet source file'>snippet source</a> | <a href='#snippet-/approvaltests-tests/src/test/java/org/packagesettings/PackageSettings.java' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

If you where to call at the org.packagesettings level.

<!-- snippet: package_level_settings_get -->
<a id='snippet-package_level_settings_get'></a>
```java
Map<String, Settings> settings = PackageLevelSettings.get();
```
<sup><a href='/approvaltests-tests/src/test/java/org/packagesettings/PackageSettingsTest.java#L13-L15' title='Snippet source file'>snippet source</a> | <a href='#snippet-package_level_settings_get' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Then you would get the following settings

<!-- snippet: /approvaltests-tests/src/test/java/org/packagesettings/PackageSettingsTest.testRetrieveValue.approved.txt -->
<a id='snippet-/approvaltests-tests/src/test/java/org/packagesettings/PackageSettingsTest.testRetrieveValue.approved.txt'></a>
```txt
lastName : Falco [from org.packagesettings.PackageSettings] 
name : Llewellyn [from org.packagesettings.PackageSettings] 
rating : 10 [from org.packagesettings.PackageSettings] 
```
<sup><a href='/approvaltests-tests/src/test/java/org/packagesettings/PackageSettingsTest.testRetrieveValue.approved.txt#L1-L3' title='Snippet source file'>snippet source</a> | <a href='#snippet-/approvaltests-tests/src/test/java/org/packagesettings/PackageSettingsTest.testRetrieveValue.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

However, if you also had

<!-- snippet: /approvaltests-tests/src/test/java/org/packagesettings/subpackage/PackageSettings.java -->
<a id='snippet-/approvaltests-tests/src/test/java/org/packagesettings/subpackage/PackageSettings.java'></a>
```java
package org.packagesettings.subpackage;

public class PackageSettings
{
  public String   name        = "Test Name";
  private boolean rating      = true;
  public String   ratingScale = "logarithmic";
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/packagesettings/subpackage/PackageSettings.java#L1-L8' title='Snippet source file'>snippet source</a> | <a href='#snippet-/approvaltests-tests/src/test/java/org/packagesettings/subpackage/PackageSettings.java' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

and you ran the same code but from the org.packagesettings.subpackage  
then you would get a blended view of the two classes where anything in the sub-package would override the parents.

<!-- snippet: /approvaltests-tests/src/test/java/org/packagesettings/subpackage/PackageSettingsTest.testRetrieveValueWithOverRide.approved.txt -->
<a id='snippet-/approvaltests-tests/src/test/java/org/packagesettings/subpackage/PackageSettingsTest.testRetrieveValueWithOverRide.approved.txt'></a>
```txt
lastName : Falco [from org.packagesettings.PackageSettings] 
name : Test Name [from org.packagesettings.subpackage.PackageSettings] 
rating : true [from org.packagesettings.subpackage.PackageSettings] 
ratingScale : logarithmic [from org.packagesettings.subpackage.PackageSettings] 
```
<sup><a href='/approvaltests-tests/src/test/java/org/packagesettings/subpackage/PackageSettingsTest.testRetrieveValueWithOverRide.approved.txt#L1-L4' title='Snippet source file'>snippet source</a> | <a href='#snippet-/approvaltests-tests/src/test/java/org/packagesettings/subpackage/PackageSettingsTest.testRetrieveValueWithOverRide.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

---

[Back to User Guide](README.md#top)
