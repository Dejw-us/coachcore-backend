import subprocess
import sys

def install_all():
  print("Installing maven...")
  should_skip_tests = False
  
  if len(sys.argv) >= 2:
    should_skip_tests = sys.argv[1] == "-DskipTests"
  else:
    should_skip_tests = input("Skip tests? yes/no: ").lower() == "yes"
    
  if should_skip_tests:
    subprocess.run(["mvn", "clean", "install", "-f", "../../pom.xml", "-DskipTests"])
  else:
    subprocess.run(["mvn", "clean", "install", "-f", "../../pom.xml"])