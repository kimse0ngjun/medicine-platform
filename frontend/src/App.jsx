import { BrowserRouter, Routes, Route } from "react-router-dom";
import RecallPage from "./pages/RecallPage";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import FindId from "./pages/FindId";
import FindPassword from "./pages/FindPassword";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<RecallPage />} />
        <Route path="/auth/login" element={<Login />} />
        <Route path="/auth/signup" element={<Signup />} />
        <Route path="/auth/find-id" element={<FindId />} />
        <Route path="/auth/find-password" element={<FindPassword />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
