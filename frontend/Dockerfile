# Use official Node image
FROM node:22-alpine

# Set working directory
WORKDIR /app

# Copy frontend files
COPY . .

# Install dependencies and build
RUN npm install && npm run build

# Use nginx to serve static files
FROM nginx:alpine
COPY --from=0 /app/dist /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
