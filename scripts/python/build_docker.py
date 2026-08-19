import glob
import os
from pathlib import Path
import shutil
import xml.etree.ElementTree as ET
import xmltodict
import docker_template
import yaml
import subprocess
import services

docker_app_template = docker_template.get_jar_docker_template()

def build_dockerfile(service_folder):
  pom_path = os.path.join("..", "..", service_folder, "pom.xml")
  pom_string = read_file_to_string(pom_path)
  pom_xml = xmltodict.parse(pom_string)
  
  (version, name) = get_project_data(pom_xml)
  
  jar_files = glob.glob(f"../../{service_folder}/target/*.jar")
  
  assert_that(jar_files, "Failed to find jar file. Try to build project with maven.")
  
  jar_file = jar_files[0]
  jar_file_name = Path(jar_file).name
  
  dockerfile_folder = os.path.join("..", "..", "docker", "generated", service_folder)
  target_folder = os.path.join(dockerfile_folder, "target")
  dockerfile = os.path.join(dockerfile_folder, "Dockerfile")
  
  os.makedirs(dockerfile_folder, exist_ok=True)
  os.makedirs(target_folder, exist_ok=True)
  
  shutil.copy(jar_file, target_folder)

  port = get_port(service_folder)
    
  with open(dockerfile, "w") as file:
    content_to_write = docker_app_template.replace("${PORT}", str(port)).replace("${APP_FILE_NAME}", jar_file_name)
    file.write(content_to_write)
    
  image_name = f"coachcore/{name}:{version}"
  subprocess.run(["docker", "build", "-t", image_name, "-f", dockerfile, dockerfile_folder])
  
  
def get_port(service_folder):
  application_yml = os.path.join("..", "..", service_folder, "src", "main", "resources", "application.yml")
  with open(application_yml, "r", encoding="utf-8") as file:
    yml = yaml.safe_load(file)
  return yml.get("server", {}).get("port", "8080")

def get_project_data(xml):
  project = xml["project"]
  version = project.get("version")
  name = project.get("artifactId")
  
  return (version, name)

def read_file_to_string(file):
  with open(file, "r", encoding="utf-8") as file:
    return file.read()

def assert_that(expression, message):
  if not expression:
    print(message)
    os._exit(1)
  
def main():
  for service in services.get_services():
    print("Building docker image for " + service)
    build_dockerfile(service)
    print("Finished building docker image for " + service)
    
main()