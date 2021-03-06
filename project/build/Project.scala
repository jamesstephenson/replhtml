import sbt._

class Project(info: ProjectInfo) extends DefaultWebProject(info)
{
    val jetty7 = "org.eclipse.jetty" % "jetty-webapp" % "7.1.6.v20100715" % "compile"
    val jetty7webSocket = "org.eclipse.jetty" % "jetty-websocket" % "7.1.6.v20100715" % "compile"
    val servlet = "javax.servlet" % "servlet-api" % "2.5" % "compile"

    //override def unmanagedClasspath = super.unmanagedClasspath +++ ("lib2" / "scala-compiler.jar")
    val scalac = "org.scala-lang" % "scala-compiler" % "2.8.0" % "compile"
    val scala = "org.scala-lang" % "scala-library" % "2.8.0" % "compile"
    
    override def mainClass = Some("ch.epfl.lamp.replhtml.ReplMain")

    lazy val setupSysProps = task {
      System.setProperty("replhtml.class.path", (runClasspath +++ Path.fromFile(buildScalaInstance.compilerJar) +++ 
      Path.fromFile(buildScalaInstance.libraryJar)).absString)
      None
    } dependsOn(compile)
    
    override def runAction = task { args => super.runAction(args).dependsOn(setupSysProps) }

    // repositories
    val scalaToolsSnapshots = "Scala Tools Repository" at "http://nexus.scala-tools.org/content/repositories/snapshots/"
    val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
    val sonatypeNexusReleases = "Sonatype Nexus Releases" at "https://oss.sonatype.org/content/repositories/releases"
    val fuseSourceSnapshots = "FuseSource Snapshot Repository" at "http://repo.fusesource.com/nexus/content/repositories/snapshots"
}
