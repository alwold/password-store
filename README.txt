To get the passwordsafe-lib dependency, go to the jpwsafe project here:

http://jpwsafe.sourceforge.net/

and download the relevant version of the PasswordSafeSWT app.  Somewhere in there should
be a passwordsafe-lib.jar file.  Grab that and install it to your local repository.

Use Maven 3.0 or higher if you need to do any release work, since it supports the git scm
URL.

Currently, these drivers are supported in the PasswordStoreDriver class:
 * oracle.jdbc.driver.OracleDriver
 * com.sybase.jdbc.SybDriver
 * com.mysql.jdbc.Driver
 * com.microsoft.sqlserver.jdbc.SQLServerDriver
 * net.sourceforge.jtds.jdbc.Driver

For the time being, you can find releases here:

http://gibson.asu.edu/nexus/content/repositories/releases/com/alwold/password-store