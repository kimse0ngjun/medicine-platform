import { BrowserRouter, Routes, Route } from "react-router-dom";
import RecallPage from "./pages/RecallPage";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import MyPage from "./pages/Mypage";
import FindId from "./pages/FindId";
import FindPassword from "./pages/FindPassword";

import { AuthProvider } from "./context/AuthContext";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<RecallPage />} />
          <Route path="/mypage" element={<MyPage />} />
          <Route path="/auth/login" element={<Login />} />
          <Route path="/auth/signup" element={<Signup />} />
          <Route path="/auth/find-id" element={<FindId />} />
          <Route path="/auth/find-password" element={<FindPassword />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
