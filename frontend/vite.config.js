import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    proxy: {
      // Todos os /clientes/* vão direto para o Micronaut (porta 8082)
      '/clientes': {
        target: 'http://localhost:8082',
        changeOrigin: true,
      },
      // /sistema redireciona para o front legado do Micronaut
      '/sistema': {
        target: 'http://localhost:8082',
        changeOrigin: true,
        rewrite: () => '/',
      },
    },
  },
})
