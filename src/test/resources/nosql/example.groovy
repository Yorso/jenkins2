// Path where 'deps.txt' file is created: project.build.outputDirectory ==> /opt/tomcat8/.jenkins/workspace/jenkins2/target/classes
def depFile = new File(project.build.outputDirectory, 'deps.txt')

// def depFile = new File('/opt/tomcat8/.jenkins', 'deps.txt')

project.dependencies.each() {
  depFile.write("${it.groupId}:${it.artifactId}:${it.version}:${project.build.outputDirectory}")
}