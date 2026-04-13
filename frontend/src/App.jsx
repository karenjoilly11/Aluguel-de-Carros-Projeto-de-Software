import Navbar from './components/Navbar';
import HeroSection from './components/HeroSection';
import SuperiorServiceSection from './components/SuperiorServiceSection';
import ServicesSection from './components/ServicesSection';
import FleetSection from './components/FleetSection';
import BenefitsSection from './components/BenefitsSection';
import SistemaSection from './components/SistemaSection';
import CTASection from './components/CTASection';
import Footer from './components/Footer';
import './App.css';

export default function App() {
  return (
    <div className="app">
      <Navbar />
      <main>
        <HeroSection />
        <SuperiorServiceSection />
        <ServicesSection />
        <FleetSection />
        <BenefitsSection />
        <SistemaSection />
        <CTASection />
      </main>
      <Footer />
    </div>
  );
}
