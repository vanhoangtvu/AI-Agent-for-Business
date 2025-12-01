// Vietnam Address API
const ADDRESS_API_BASE = 'https://provinces.open-api.vn/api';

export interface Province {
  code: number;
  name: string;
  division_type: string;
  codename: string;
  phone_code: number;
}

export interface District {
  code: number;
  name: string;
  division_type: string;
  codename: string;
  province_code: number;
}

export interface Ward {
  code: number;
  name: string;
  division_type: string;
  codename: string;
  district_code: number;
}

class AddressAPI {
  // Lấy danh sách tỉnh/thành phố
  async getProvinces(): Promise<Province[]> {
    try {
      const response = await fetch(`${ADDRESS_API_BASE}/p/`);
      if (!response.ok) throw new Error('Failed to fetch provinces');
      return await response.json();
    } catch (error) {
      console.error('Error fetching provinces:', error);
      return [];
    }
  }

  // Lấy danh sách quận/huyện theo tỉnh
  async getDistricts(provinceCode: number): Promise<District[]> {
    try {
      const response = await fetch(`${ADDRESS_API_BASE}/p/${provinceCode}?depth=2`);
      if (!response.ok) throw new Error('Failed to fetch districts');
      const data = await response.json();
      return data.districts || [];
    } catch (error) {
      console.error('Error fetching districts:', error);
      return [];
    }
  }

  // Lấy danh sách phường/xã theo quận/huyện
  async getWards(districtCode: number): Promise<Ward[]> {
    try {
      const response = await fetch(`${ADDRESS_API_BASE}/d/${districtCode}?depth=2`);
      if (!response.ok) throw new Error('Failed to fetch wards');
      const data = await response.json();
      return data.wards || [];
    } catch (error) {
      console.error('Error fetching wards:', error);
      return [];
    }
  }

  // Tìm kiếm tỉnh/thành
  async searchProvinces(query: string): Promise<Province[]> {
    try {
      const response = await fetch(`${ADDRESS_API_BASE}/p/search/?q=${encodeURIComponent(query)}`);
      if (!response.ok) throw new Error('Failed to search provinces');
      return await response.json();
    } catch (error) {
      console.error('Error searching provinces:', error);
      return [];
    }
  }

  // Tìm kiếm quận/huyện
  async searchDistricts(query: string, provinceCode?: number): Promise<District[]> {
    try {
      const url = provinceCode 
        ? `${ADDRESS_API_BASE}/d/search/?q=${encodeURIComponent(query)}&p=${provinceCode}`
        : `${ADDRESS_API_BASE}/d/search/?q=${encodeURIComponent(query)}`;
      const response = await fetch(url);
      if (!response.ok) throw new Error('Failed to search districts');
      return await response.json();
    } catch (error) {
      console.error('Error searching districts:', error);
      return [];
    }
  }

  // Tìm kiếm phường/xã
  async searchWards(query: string, districtCode?: number, provinceCode?: number): Promise<Ward[]> {
    try {
      let url = `${ADDRESS_API_BASE}/w/search/?q=${encodeURIComponent(query)}`;
      if (districtCode) url += `&d=${districtCode}`;
      else if (provinceCode) url += `&p=${provinceCode}`;
      
      const response = await fetch(url);
      if (!response.ok) throw new Error('Failed to search wards');
      return await response.json();
    } catch (error) {
      console.error('Error searching wards:', error);
      return [];
    }
  }

  // Format địa chỉ đầy đủ
  formatAddress(street: string, ward: string, district: string, province: string): string {
    return `${street}, ${ward}, ${district}, ${province}`;
  }
}

export const addressAPI = new AddressAPI();
