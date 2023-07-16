import './App.css';
import AdminPanel from './pages/AdminPanel';
import { AuthProvider } from './contexts/AuthContext';

function App() {
  return (
    <AuthProvider>
    <div className="App">
      <AdminPanel />
    </div>
    </AuthProvider>
  );
}

export default App;
