/** @type {import('next').NextConfig} */
const nextConfig = {
  images: {
    domains: ["images.unsplash.com", "istockphoto.com"],
  },
  experimental: {
    appDir: true,
  },
};

module.exports = nextConfig;
