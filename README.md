# docker build

```
docker-compose up -d --build orderservice
```

# testing

curl --request POST http://localhost:8001/orders

# vegeta attack

```
echo "POST http://localhost:8001/orders" | vegeta attack -rate=50 -duration=60s | tee results.bin | vegeta report
```