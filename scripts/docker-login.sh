read -p 'Token: ' TOKEN
read -p 'Username: ' USERNAME
echo $TOKEN | docker login ghcr.io -u $USERNAME --password-stdin
