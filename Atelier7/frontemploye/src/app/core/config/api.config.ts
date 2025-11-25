// Configuration de l'URL de l'API
// En développement : http://localhost:8080/api
// En production Docker : /api (via proxy Nginx)
export const API_BASE_URL = (() => {
  // Détecter si on est dans Docker (production) ou en développement
  if (typeof window !== 'undefined') {
    const hostname = window.location.hostname;
    // Si on est sur localhost, utiliser l'URL complète (dev)
    // Sinon, utiliser le chemin relatif (Docker avec proxy Nginx)
    return hostname === 'localhost' ? 'http://localhost:8080/api' : '/api';
  }
  return 'http://localhost:8080/api';
})();


