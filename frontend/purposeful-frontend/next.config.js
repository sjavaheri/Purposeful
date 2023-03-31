/** @type {import('next').NextConfig} */
const nextConfig = {
  images: {
    domains: ["images.unsplash.com", "pixabay.com/photos"],
  },
  experimental: {
    appDir: true,
  },
};

module.exports = nextConfig;
