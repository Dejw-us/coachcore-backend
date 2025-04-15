# Python scripts

## How to build docker images?

### Run commands

- `source venv/bin/activate` - activates env
- `pip install -r requirements.txt` - installs dependencies
- `./mvnv install -DskipTests` - builds jar files
- Clear services in docker folder
- `python build_docker.py` - builds docker files and images
