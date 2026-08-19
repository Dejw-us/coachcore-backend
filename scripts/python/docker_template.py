def get_jar_docker_template():
  with open("../docker-templates/jar.template.Dockerfile", "r") as file:
    return file.read()