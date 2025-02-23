import subprocess
import sys
import docker_template
import maven
import os
import glob
import shutil;
from pathlib import Path
import services

docker_app_template = docker_template.get_jar_docker_template()

if input("Do you want to build maven? yes/no: ").lower() == "yes":
  maven.install_all()

docker_dir = "../../docker"

os.makedirs(docker_dir, exist_ok=True)

should_build_docker = input("Do you want to build docker images from created files? yes/no: ").lower() == "yes"

def create_docker_file(app_name, port):
  jar_files = glob.glob(f"../../{app_name}/target/*.jar")
  
  if not jar_files:
    print(f"Failed to find jar for {app_name} Returning...")
    return
  
  jar_file = jar_files[0]
  jar_file_name = Path(jar_file).name
  app_dir = f"{docker_dir}/{app_name}"
  target_dir=f"{app_dir}/target"
  docker_file_name = f"{app_dir}/Dockerfile"
  
  print(f"Found jar file: {jar_file_name}\nCreating Dockerfile...")
  
  os.makedirs(app_dir, exist_ok=True)
  os.makedirs(target_dir, exist_ok=True)
  shutil.copy(jar_file, target_dir)
  
  with open(docker_file_name, "w") as file:
    content_to_write = docker_app_template.replace("${PORT}", str(port)).replace("${APP_FILE_NAME}", jar_file_name)
    file.write(content_to_write)
  
  if should_build_docker:
    should_build = input(f"Do you want to build docker image for {app_name}? yes/no: ").lower() == "yes"
    if (should_build):
      while True:
        tag = input(f"Enter version tag for {app_name}: ")
        if (len(tag) < 1):
          print("Version tag must be at least one character.")
          continue
        image_name = f"{app_name}:{tag}"
        subprocess.run(["docker", "build", "-t", image_name, "-f", docker_file_name, app_dir])
        print(f"Created docker image {image_name}")
        break
  
for service, port in services.get_services().items():
  create_docker_file(service, port)