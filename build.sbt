import sbt.librarymanagement
name := "scafi-experiment"

version := "0.1"

scalaVersion := "3.3.3"
// build.sbt
val scafi_version = "1.3.0"

val scafi_core  =  "it.unibo.scafi" %% "scafi-core" % scafi_version
val scafi_simulator  =  "it.unibo.scafi" %% "scafi-simulator" % scafi_version
val scafi_simulator_gui =  "it.unibo.scafi" %% "simulator-gui-new" % scafi_version

val javafx_version = "21.0.4"
val javafx_modules = Seq("base", "controls", "fxml", "graphics", "media", "swing")

val javafx_classifier: String = {
  val os   = sys.props.getOrElse("os.name", "").toLowerCase
  val arch = sys.props.getOrElse("os.arch", "").toLowerCase
  val osPart =
    if (os.contains("mac"))                       "mac"
    else if (os.contains("win"))                  "win"
    else if (os.contains("nux") || os.contains("nix")) "linux"
    else sys.error(s"Unsupported OS for JavaFX: $os")
  val archPart =
    if (arch.contains("aarch64") || arch.contains("arm64")) "-aarch64"
    else if (arch.contains("64")) ""
    else sys.error(s"Unsupported arch for JavaFX: $arch")
  osPart + archPart
}

libraryDependencies ++= Seq(
  scafi_simulator_gui.cross(CrossVersion.for3Use2_13),
  scafi_core.cross(CrossVersion.for3Use2_13),
  scafi_simulator.cross(CrossVersion.for3Use2_13)
) ++ javafx_modules.map(m => "org.openjfx" % s"javafx-$m" % javafx_version classifier javafx_classifier)
fork := true