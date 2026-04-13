import { motion } from 'framer-motion';

export default function CTASection() {
  return (
    <section className="cta-section" id="reservar">
      {/* Glow decorativo */}
      <div className="cta-glow" aria-hidden="true" />

      <div className="section-container">
        <motion.div
          className="cta-card"
          initial={{ opacity: 0, scale: 0.96, y: 40 }}
          whileInView={{ opacity: 1, scale: 1, y: 0 }}
          viewport={{ once: true }}
          transition={{ duration: 0.8, ease: [0.16, 1, 0.3, 1] }}
        >
          {/* Linhas decorativas de neon */}
          <div className="cta-neon-line cta-neon-line--top" />
          <div className="cta-neon-line cta-neon-line--bottom" />

          <span className="section-tag">Pronto para dirigir?</span>
          <h2 className="cta-title">
            Reserve seu veículo<br />
            <span className="accent-text">agora mesmo</span>
          </h2>
          <p className="cta-desc">
            Acesse o sistema completo de gestão para realizar sua reserva,
            cadastro e acompanhar seu histórico de locações.
          </p>

          <div className="cta-actions">
            <motion.a
              href="/sistema"
              className="btn-primary-hero"
              whileHover={{ scale: 1.04 }}
              whileTap={{ scale: 0.97 }}
            >
              Acessar o Sistema
              <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
                <path d="M3 8h10M9 4l4 4-4 4" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"/>
              </svg>
            </motion.a>
            <a href="tel:+5511999999999" className="btn-secondary-hero">
              Falar com consultor
            </a>
          </div>

          {/* Destaques rápidos */}
          <div className="cta-highlights">
            {['Reserva em 2 min', 'Sem taxa de cancelamento', 'Entrega incluída'].map((h) => (
              <span key={h} className="cta-highlight-item">
                <svg width="14" height="14" viewBox="0 0 14 14" fill="none">
                  <path d="M2 7l3 3 7-7" stroke="#2B4CFF" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"/>
                </svg>
                {h}
              </span>
            ))}
          </div>
        </motion.div>
      </div>
    </section>
  );
}
