import subprocess

subprocess.run(["docker", "compose", "-f" , "../../docker/compose/dev/db.yml", "up"])