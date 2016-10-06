// def depFile = new File(project.build.outputDirectory, 'deps.txt')
def depFile = new File('/opt/tomcat8/.jenkins', 'deps.txt')

project.dependencies.each() {
  depFile.write("${it.groupId}:${it.artifactId}:${it.version}")
}