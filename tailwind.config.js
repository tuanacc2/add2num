module.exports = {
  purge: [],
  darkMode: false, // or 'media' or 'class'
  content: [
    "./src/main/resources/templates/**/*.html", // Quét tất cả file html trong templates
    "./src/main/resources/static/**/*.js"        // Quét file js nếu có dùng class Tailwind
  ],
  theme: {
    extend: {},
  },
  variants: {
    extend: {},
  },
  plugins: [],
}
